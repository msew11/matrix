package org.matrix.game.server.home.actor

import akka.actor.AbstractActorWithStash
import akka.actor.PoisonPill
import akka.actor.Props
import akka.actor.ReceiveTimeout
import akka.cluster.sharding.ShardRegion
import org.matrix.game.common.akka.ClientMessage2Home
import org.matrix.game.common.log.logInfo


class PlayerActor(
) : AbstractActorWithStash() {

    companion object {
        fun props(): Props {
            return Props.create(PlayerActor::class.java) {
                PlayerActor()
            }
        }
    }

    override fun createReceive(): Receive {
        return receiveBuilder()
            .match(ClientMessage2Home::class.java, ::handleMsg)
            .matchEquals(ReceiveTimeout.getInstance(), ::passivate)
            .matchAny(::dealAny)
            .build()
    }

    private fun handleMsg(msg: ClientMessage2Home) {
        logInfo { "PlayerActor收到消息 ${msg.playerId}" }
    }

    private fun dealAny(msg: Any) {
        logInfo { "PlayerActor dealAny" }
    }

    private fun passivate(msg: Any) {
        logInfo { "PlayerActor passivate" }
        context.parent.tell(ShardRegion.Passivate(PoisonPill.getInstance()), self)
    }
}