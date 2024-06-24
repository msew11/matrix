package org.matrix.game.common.network.netty.handler

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.util.CharsetUtil
import org.matrix.game.proto.HelloMatrix
import org.matrix.game.proto.Test
import org.matrix.game.proto.Test2

class MyClientHandler : ChannelInboundHandlerAdapter() {
    override fun channelActive(ctx: ChannelHandlerContext) {
        for (i in 1..5) {
            val test2 = Test2.newBuilder()
                .setId(i)
                .setContent("Test2")
            val test = Test.newBuilder()
                .setId(i)
                .setContent("Test")

            val req = HelloMatrix.newBuilder()
                .setTest2(test2)
                .setTest(test)
                .build()

            ctx.writeAndFlush(req)
        }
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val byteBuf = msg as ByteBuf
        println("收到服务端${ctx.channel().remoteAddress()}的消息：${byteBuf.toString(CharsetUtil.UTF_8)}")
    }
}
