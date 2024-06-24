package org.matrix.game.common.network.netty.handler

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.util.CharsetUtil
import org.matrix.game.proto.HelloMatrix
import org.matrix.game.proto.Test
import org.matrix.game.proto.Test2

class MyServerHandler : SimpleChannelInboundHandler<HelloMatrix>() {

    override fun channelRead0(ctx: ChannelHandlerContext, msg: HelloMatrix) {


        when(msg.payloadCase) {
            HelloMatrix.PayloadCase.TEST ->{
                val payload = msg.getField(HelloMatrix.getDescriptor().findFieldByNumber(msg.payloadCase.number)) as Test
                println("收到客户端[${ctx.channel().remoteAddress()}]发送的[${msg.payloadCase}]：${payload.content}")
            }
            HelloMatrix.PayloadCase.TEST2 ->{
                val payload = msg.getField(HelloMatrix.getDescriptor().findFieldByNumber(msg.payloadCase.number)) as Test2
                println("收到客户端[${ctx.channel().remoteAddress()}]发送的[${msg.payloadCase}]：${payload.content}")
            }
            else -> {

            }
        }

    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已收到消息，并给你发送一个问号?", CharsetUtil.UTF_8));
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        println(cause.message)
        ctx.close()
    }

    /*override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
    }

    */


}
