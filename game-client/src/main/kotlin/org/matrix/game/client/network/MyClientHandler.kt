package org.matrix.game.client.network

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.util.CharsetUtil
import org.matrix.game.proto.client.ClientReq
import org.matrix.game.proto.client.DoSomeAction
import org.matrix.game.proto.client.LoginGame

class MyClientHandler : ChannelInboundHandlerAdapter() {

    lateinit var ctx: ChannelHandlerContext

    override fun channelActive(ctx: ChannelHandlerContext) {
        this.ctx = ctx

        val playerId: Long = 10000001

        loginGame(playerId)
        doSomeAction("这是一次行为a")
        doSomeAction("这是一次行为b")
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val byteBuf = msg as ByteBuf
        println("收到服务端${ctx.channel().remoteAddress()}的消息：${byteBuf.toString(CharsetUtil.UTF_8)}")
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
