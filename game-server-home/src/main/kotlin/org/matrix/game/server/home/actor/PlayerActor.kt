package org.matrix.game.server.home.actor

import akka.actor.AbstractActorWithStash
import akka.actor.PoisonPill
import akka.actor.Props
import akka.actor.ReceiveTimeout
import akka.cluster.sharding.ShardRegion
import org.matrix.game.common.akka.ClientReq2Home
import org.matrix.game.core.log.logger
import org.matrix.game.proto.home.HomeMessage
import org.matrix.game.server.home.home

class PlayerActor(
) : AbstractActorWithStash() {

    companion object {
        val logger by logger()
        fun props(): Props {
            return Props.create(PlayerActor::class.java) {
                PlayerActor()
            }
        }
    }

    override fun createReceive(): Receive {
        return receiveBuilder()
            .match(ClientReq2Home::class.java, ::handleClientReq2Home)
            .match(HomeMessage::class.java, ::handleHomeMessage)
            .matchEquals(ReceiveTimeout.getInstance(), ::passivate)
            .matchAny(::dealAny)
            .build()
    }

    private fun handleClientReq2Home(msg: ClientReq2Home) {
        logger.info { "$self 收到消息 ${msg.playerId}" }
    }

    private fun handleHomeMessage(msg: HomeMessage) {
        val sender = sender
        val handler = home.fetchMessageHandler(msg.msgName)
        if (handler == null) {
            logger.error { "$self 未找到消息处理器 ${msg.playerId} ${msg.msgName}" }
        } else {
            try {
                handler.deal(msg.payload.unpack(handler.msgType))
                    .whenComplete { resp, err ->
                        when {
                            err != null -> {
                                // TODO 异常处理
                            }
                            resp != null -> {
                                sender.tell(resp, null)
                            }

                            else -> {}
                        }
                    }
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