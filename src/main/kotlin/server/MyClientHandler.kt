package org.matrix.server

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.util.CharsetUtil
import io.netty.util.internal.StringUtil
import java.nio.charset.Charset

class MyClientHandler : ChannelInboundHandlerAdapter() {
    override fun channelActive(ctx: ChannelHandlerContext) {
        for (i in 1..5) {

            // MessagePojo.Message.newBuilder().setId()

            val byteBuf = Unpooled.copiedBuffer("msg No$i ${StringUtil.LINE_FEED}", Charset.forName("utf-8"))
            // val byteBuf = Unpooled.copiedBuffer("msg No$i ", Charset.forName("utf-8"))
            ctx.writeAndFlush(byteBuf)
        }
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val byteBuf = msg as ByteBuf
        println("收到服务端${ctx.channel().remoteAddress()}的消息：${byteBuf.toString(CharsetUtil.UTF_8)}")
    }
}