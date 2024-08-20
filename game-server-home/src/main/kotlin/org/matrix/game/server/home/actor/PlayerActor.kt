package org.matrix.game.server.home.actor

import akka.actor.ActorRef
import akka.actor.Cancellable
import akka.actor.PoisonPill
import akka.actor.Props
import akka.cluster.sharding.ShardRegion
import org.matrix.game.common.component.CompDb
import org.matrix.game.common.constg.AKKA_MAILBOX_SMALL
import org.matrix.game.common.heart.HeartEvent
import org.matrix.game.core.akka.ShardFuncActor
import org.matrix.game.core.akka.Worker
import org.matrix.game.core.concurrent.AcsFactory
import org.matrix.game.core.log.logger
import org.matrix.game.proto.home.HomeMessage
import org.matrix.game.server.home.component.CompHomeMessage
import org.matrix.game.server.home.handler.HandlerContext
import scala.concurrent.duration.FiniteDuration
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

const val H_ACTOR_DISPATCHER = "akka.actor.h-shard"
const val H_COMPUTE_DISPATCHER = "akka.actor.h-compute"
const val H_DEFAULT_IO_DISPATCHER = "akka.actor.h-default-io"

class PlayerActor(val compDb: CompDb, val compHomeMessage: CompHomeMessage) : ShardFuncActor() {

    /** 用于发起异步请求，不要直接在外部使用这个[acsFactory]创建acs！**/
    lateinit var acsFactory: AcsFactory

    var heartSchedule: Cancellable? = null

    var playerId: Long = 0

    val dcm = PlayerDcManager(this) {
        compDb.dao
    }

    object WorkerName {

        @JvmStatic
        val dbRead = "dbRead"
    }

    companion object {
        val logger by logger()
        fun props(compDb: CompDb, compHomeMessage: CompHomeMessage): Props {
            return Props.create(PlayerActor::class.java) {
                PlayerActor(compDb, compHomeMessage)
            }.withDispatcher(H_ACTOR_DISPATCHER)
        }
    }

    override fun createReceive(): Receive {
        return receiveBuilder()
            .match(Runnable::class.java, ::handleAcsCallback)
            .match(HomeMessage::class.java, ::handleHomeMessage)
            .match(HeartEvent::class.java, ::handleHeartEvent) // 处理心跳消息
            //.matchEquals(ReceiveTimeout.getInstance(), ::passivate)
            .matchAny(::dealAny)
            .build()
    }

    override fun preStart() {

        val dbWriteWorker: ActorRef =
            context.actorOf(Worker.props("playerIoWorker", H_DEFAULT_IO_DISPATCHER, AKKA_MAILBOX_SMALL))
        val dbReadWorker: ActorRef =
            context.actorOf(Worker.props("playerDbReadWorker", H_DEFAULT_IO_DISPATCHER, AKKA_MAILBOX_SMALL))
        val computationWorker: ActorRef =
            context.actorOf(Worker.props("playerComputationWorker", H_COMPUTE_DISPATCHER, AKKA_MAILBOX_SMALL))

        // 异步工厂
        acsFactory = AcsFactory(
            mainActor = self,
            defaultIoWorker = dbWriteWorker,
            defaultComputationWorker = computationWorker,
            namedWorkers = mapOf(
                WorkerName.dbRead to dbReadWorker,
            ),
            delayer = ScheduledThreadPoolExecutor(10)
        )

        // 启动心跳
        val initialDelay = FiniteDuration.apply(1L, TimeUnit.SECONDS)
        val interval = FiniteDuration.apply(1L, TimeUnit.SECONDS)
        this.heartSchedule = context.system().scheduler().scheduleAtFixedRate(
            initialDelay,
            interval,
            self,
            HeartEvent(),
            context.dispatcher(),
            ActorRef.noSender()
        )
    }

    private fun handleAcsCallback(runnable: Runnable) {
        try {
            runnable.run()
        } catch (e: Exception) {
            logger.error(e) { "处理异步回调异常" }
        }
    }

    private fun handleHomeMessage(msg: HomeMessage) {
        try {
            val sender = sender
            playerId = msg.playerId
            val handler = compHomeMessage.fetchHandler(msg.msgName)
            if (handler == null) {
                logger.error { "$self 未找到消息处理器 ${msg.playerId} ${msg.msgName}" }
            } else {
                handler.deal(HandlerContext(dcm, sender), msg.payload.unpack(handler.msgType))
            }
        } catch (e: Exception) {
            logger.error(e) { "消息处理异常" }
        }
    }

    private fun handleHeartEvent(event: HeartEvent) {
        try {

        } catch (e: Exception) {
            logger.error(e) { "心跳处理异常" }
        }
    }

    private fun dealAny(msg: Any) {
        logger.info { "$self dealAny" }
    }

    private fun passivate(msg: Any) {
        logger.info { "$self passivate" }
        context.parent.tell(ShardRegion.Passivate(PoisonPill.getInstance()), self)
    }
}