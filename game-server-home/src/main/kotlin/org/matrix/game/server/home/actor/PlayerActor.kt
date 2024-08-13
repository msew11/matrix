package org.matrix.game.server.home.actor

import akka.actor.AbstractActorWithStash
import akka.actor.PoisonPill
import akka.actor.Props
import akka.actor.ReceiveTimeout
import akka.cluster.sharding.ShardRegion
import org.matrix.game.common.akka.ClientReq2Home
import org.matrix.game.core.log.logger

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
            .match(ClientReq2Home::class.java, ::handleMsg)
            .matchEquals(ReceiveTimeout.getInstance(), ::passivate)
            .matchAny(::dealAny)
            .build()
    }

    private fun handleMsg(msg: ClientReq2Home) {
        logger.info { "$self 收到消息 ${msg.playerId}" }
    }

    private fun dealAny(msg: Any) {
        logger.info { "$self dealAny" }
    }

    private fun passivate(msg: Any) {
        logger.info { "$self passivate" }
        context.parent.tell(ShardRegion.Passivate(PoisonPill.getInstance()), self)
    }
}