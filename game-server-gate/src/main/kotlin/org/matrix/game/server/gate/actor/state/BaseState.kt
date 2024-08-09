package org.matrix.game.server.gate.actor.state

import akka.actor.AbstractActor
import org.matrix.game.common.log.logError
import org.matrix.game.proto.c2s.GameReq
import org.matrix.game.server.gate.actor.ChannelActor
import org.matrix.game.server.gate.actor.NettyChannelInactive

abstract class BaseState(val actor: ChannelActor) {

    fun createReceive(): AbstractActor.Receive {
        return actor.receiveBuilder()
            .match(NettyChannelInactive::class.java, ::handleNettyChannelInactive)
            .matchAny { handleMsg(it) }
            .build()
    }

    private fun handleNettyChannelInactive(msg: NettyChannelInactive) {
        actor.expired()
    }

    private fun handleMsg(msg: Any) {
        try {
            when (msg) {
                is GameReq -> handleGameReq(msg)
                else -> {
                    logError { "@${this::class.java.simpleName} 收到${msg::class.java}消息，无法处理！" }
                }
            }
        } catch (e: Exception) {
            logError(e) { "处理消息发生异常" }
            actor.expired()
        }
    }

    abstract fun handleGameReq(msg: GameReq)
}