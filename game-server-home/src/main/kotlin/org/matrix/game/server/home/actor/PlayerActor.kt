package org.matrix.game.server.home.actor

import akka.actor.AbstractActor
import akka.actor.Props
import org.matrix.game.proto.c2s.GameReq

class PlayerActor(
) : AbstractActor() {

    companion object {
        fun props(): Props {
            return Props.create(PlayerActor::class.java) {
                PlayerActor()
            }
        }
    }

    override fun createReceive(): Receive {
        return receiveBuilder()
            .match(GameReq::class.java, ::handleMsg)
            .build()
    }

    private fun handleMsg(msg: GameReq) {
    }
}