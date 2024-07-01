package org.matrix.game.server.gateway.actor

import akka.actor.AbstractActor
import akka.actor.Props
import io.netty.channel.ChannelHandlerContext
import org.matrix.game.proto.c2s.GameReq
import org.matrix.game.proto.c2s.Test
import org.matrix.game.proto.c2s.Test2

class ChannelActor(
    val ctx: ChannelHandlerContext
) : AbstractActor() {

    companion object {
        fun props(ctx: ChannelHandlerContext): Props {
            return Props.create(ChannelActor::class.java) {
                ChannelActor(ctx)
            }
        }
    }

    override fun createReceive(): Receive {
        return receiveBuilder()
            .match(GameReq::class.java, ::handleMsg)
            .build()
    }

    private fun handleMsg(msg: GameReq) {
        when (msg.payloadCase) {
            GameReq.PayloadCase.TEST -> {
                val payload =
                    msg.getField(GameReq.getDescriptor().findFieldByNumber(msg.payloadCase.number)) as Test
                println("收到消息：[${msg.payloadCase}]：${payload.content}")
            }

            GameReq.PayloadCase.TEST2 -> {
                val payload =
                    msg.getField(GameReq.getDescriptor().findFieldByNumber(msg.payloadCase.number)) as Test2
                println("收到消息：[${msg.payloadCase}]：${payload.content}")
            }

            else -> {

            }
        }
    }
}