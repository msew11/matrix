package org.matrix.game.client.network

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.util.CharsetUtil
import org.matrix.game.proto.c2s.EnterGame
import org.matrix.game.proto.c2s.GameReq

class MyClientHandler : ChannelInboundHandlerAdapter() {
    override fun channelActive(ctx: ChannelHandlerContext) {
        val enterGame = EnterGame.newBuilder()
            .setPlayerId(123)

        val req = GameReq.newBuilder()
            .setEnterGame(enterGame)
            .build()
        ctx.writeAndFlush(req)
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val byteBuf = msg as ByteBuf
        println("收到服务端${ctx.channel().remoteAddress()}的消息：${byteBuf.toString(CharsetUtil.UTF_8)}")
    }
}
