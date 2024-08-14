package org.matrix.game.server.gate.actor.state

import akka.actor.AbstractActor
import org.matrix.game.core.log.logger
import org.matrix.game.proto.client.ClientReq
import org.matrix.game.proto.client.ClientResp
import org.matrix.game.server.gate.actor.ChannelActor
import org.matrix.game.server.gate.actor.NettyChannelInactive

abstract class BaseState(val actor: ChannelActor) {

    companion object {
        val logger by logger()
    }

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
                is ClientReq -> handleClientReq(msg)
                is ClientResp -> handleClientResp(msg)
                else -> {
                    logger.error { "@${this::class.java.simpleName} 收到${msg::class.java}消息，无法处理！" }
                }
            }
        } catch (e: Exception) {
            logger.error(e) { "处理消息发生异常" }
            actor.expired()
        }
    }

    abstract fun handleClientReq(msg: ClientReq)

    abstract fun handleClientResp(msg: ClientResp)
}