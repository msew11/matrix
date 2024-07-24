package org.matrix.game.server.gate.actor

import akka.actor.AbstractActor
import akka.actor.Props
import io.netty.channel.ChannelHandlerContext
import org.matrix.game.proto.c2s.EnterGame
import org.matrix.game.proto.c2s.GameReq
import org.matrix.game.proto.c2s.NumberMsg
import org.matrix.game.proto.c2s.StringMsg

class ChannelActor(
    val ctx: ChannelHandlerContext
) : AbstractActor() {

    var playerId: Long = 0
        private set

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
            GameReq.PayloadCase.ENTERGAME -> {
                val payload =
                    msg.getField(GameReq.getDescriptor().findFieldByNumber(msg.payloadCase.number)) as EnterGame
                println("收到消息：[${msg.payloadCase}]：${payload.playerId}")
            }

            GameReq.PayloadCase.STRINGMSG -> {
                val payload =
                    msg.getField(GameReq.getDescriptor().findFieldByNumber(msg.payloadCase.number)) as StringMsg
                println("收到消息：[${msg.payloadCase}]：${payload.content}")
            }

            GameReq.PayloadCase.NUMBERMSG -> {
                val payload =
                    msg.getField(GameReq.getDescriptor().findFieldByNumber(msg.payloadCase.number)) as NumberMsg
                println("收到消息：[${msg.payloadCase}]：${payload.count}")
            }

            else -> {

            }
        }
    }

    fun savePlayerId(playerId: Long) {
        this.playerId = playerId
    }
}