package org.matrix.game.server.gateway.network

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.util.CharsetUtil
import org.matrix.game.proto.c2s.GameReq
import org.matrix.game.server.gateway.actor.ChannelActor
import org.matrix.game.server.gateway.gateway

class MyServerHandler : SimpleChannelInboundHandler<GameReq>() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        val actorRef = gateway.compAkka.actorSystem.actorOf(ChannelActor.props(ctx))
    }

    override fun channelRead0(ctx: ChannelHandlerContext, msg: GameReq) {


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
