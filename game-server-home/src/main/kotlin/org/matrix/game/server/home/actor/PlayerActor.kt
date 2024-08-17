package org.matrix.game.server.home.actor

import akka.actor.ActorRef
import akka.actor.PoisonPill
import akka.actor.Props
import akka.actor.ReceiveTimeout
import akka.cluster.sharding.ShardRegion
import org.matrix.game.common.constg.AKKA_MAILBOX_SMALL
import org.matrix.game.core.akka.ShardFuncActor
import org.matrix.game.core.akka.Worker
import org.matrix.game.core.concurrent.AcsFactory
import org.matrix.game.core.log.logger
import org.matrix.game.proto.home.HomeMessage
import org.matrix.game.server.home.handler.HandlerContext
import org.matrix.game.server.home.home
import java.util.concurrent.ScheduledThreadPoolExecutor

const val H_ACTOR_DISPATCHER = "akka.actor.h-shard"
const val H_COMPUTE_DISPATCHER = "akka.actor.h-compute"
const val H_DEFAULT_IO_DISPATCHER = "akka.actor.h-default-io"

class PlayerActor : ShardFuncActor() {

    /** 用于发起异步请求，不要直接在外部使用这个[acsFactory]创建acs！**/
    lateinit var acsFactory: AcsFactory

    var playerId: Long = 0

    val dbm = PlayerDbManager(this) {
        home.compDb.dao
    }

    object WorkerName {

        @JvmStatic
        val dbRead = "dbRead"
    }

    companion object {
        val logger by logger()
        fun props(): Props {
            return Props.create(PlayerActor::class.java) {
                PlayerActor()
            }.withDispatcher(H_ACTOR_DISPATCHER)
        }
    }

    override fun createReceive(): Receive {
        return receiveBuilder()
            .match(Runnable::class.java, ::handleAcsCallback)
            .match(HomeMessage::class.java, ::handleHomeMessage)
            .matchEquals(ReceiveTimeout.getInstance(), ::passivate)
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

        acsFactory = AcsFactory(
            mainActor = self,
            defaultIoWorker = dbWriteWorker,
            defaultComputationWorker = computationWorker,
            namedWorkers = mapOf(
                WorkerName.dbRead to dbReadWorker,
            ),
            delayer = ScheduledThreadPoolExecutor(10)
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
        val sender = sender
        playerId = msg.playerId
        val handler = home.fetchMessageHandler(msg.msgName)
        if (handler == null) {
            logger.error { "$self 未找到消息处理器 ${msg.playerId} ${msg.msgName}" }
        } else {
            try {
                handler.deal(HandlerContext(dbm, sender), msg.payload.unpack(handler.msgType))
            } catch (e: Exception) {
                logger.error(e) { "消息处理异常" }
            }
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