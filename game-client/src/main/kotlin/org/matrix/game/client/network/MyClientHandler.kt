package org.matrix.game.client.network

import com.google.protobuf.Message
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import org.matrix.game.core.log.logger
import org.matrix.game.proto.client.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MyClientHandler : ChannelInboundHandlerAdapter() {

    companion object {
        val logger by logger()
    }

    lateinit var ctx: ChannelHandlerContext

    override fun channelActive(ctx: ChannelHandlerContext) {
        this.ctx = ctx

        thread {
            val playerId: Long = 10000001

            loginGame(playerId)
            for (i in 1..1000) {
                doSomeAction("这是一次行为${i}")
                TimeUnit.SECONDS.sleep(1)
            }
        }
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        msg as ClientResp
        val payload = msg.getField(ClientResp.getDescriptor().findFieldByNumber(msg.payloadCase.number)) as Message
        when {
            payload is DoSomeActionRt -> {
                logger.info { "收到服务端${ctx.channel().remoteAddress()}的消息：${payload.msg}" }
            }

            else -> {
                logger.info { "收到服务端${ctx.channel().remoteAddress()}的消息：${payload.javaClass.simpleName}" }
            }
        }
    }

    private fun loginGame(playerId: Long) {

        val loginGame = LoginGame.newBuilder()
            .setPlayerId(playerId)

        val req = ClientReq.newBuilder()
            .setLoginGame(loginGame)
            .build()
        ctx.writeAndFlush(req)
    }

    private fun doSomeAction(desc: String) {

        val doSomeAction = DoSomeAction.newBuilder()
            .setDesc(desc)

        val req = ClientReq.newBuilder()
            .setDoSomeAction(doSomeAction)
            .build()
        ctx.writeAndFlush(req)
    }
}
