package org.matrix.game.server.gate.actor

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.PoisonPill
import akka.actor.Props
import io.netty.channel.ChannelHandlerContext
import org.matrix.game.common.log.logInfo
import org.matrix.game.server.gate.actor.state.BaseState
import org.matrix.game.server.gate.actor.state.BeforeLoginState

class ChannelActor(
    val ctx: ChannelHandlerContext
) : AbstractActor() {

    var playerId: Long = 0
        private set

    private var currentState: BaseState = BeforeLoginState(this)

    companion object {
        fun props(ctx: ChannelHandlerContext): Props {
            return Props.create(ChannelActor::class.java) {
                ChannelActor(ctx)
            }
        }
    }

    override fun createReceive(): Receive {

        logInfo { "ChannelActor ${self.path()} createReceive" }
        return currentState.createReceive()
    }

    fun changeState(newState: BaseState) {
        currentState = newState
        context.become(currentState.createReceive())
    }

    fun expired() {
        ctx.disconnect()
        context.self.tell(PoisonPill.getInstance(), ActorRef.noSender())

        logInfo { "ChannelActor ${self.path()} expired" }
    }

    fun savePlayerId(playerId: Long) {
        this.playerId = playerId
    }

    override fun postStop() {
        logInfo { "ChannelActor ${self.path()} stop" }
    }
}