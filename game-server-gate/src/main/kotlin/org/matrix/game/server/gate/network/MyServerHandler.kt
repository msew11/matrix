package org.matrix.game.server.gate.network

import akka.actor.ActorRef
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.util.AttributeKey
import io.netty.util.CharsetUtil
import org.matrix.game.common.log.logInfo
import org.matrix.game.proto.c2s.GameReq
import org.matrix.game.server.gate.actor.ChannelActor
import org.matrix.game.server.gate.gate

/**
 * @see <a href="https://cloud.tencent.com/developer/article/2182854">Netty·Handler 对比</a>
 */
class MyServerHandler : SimpleChannelInboundHandler<GameReq>() {

    data class ClientInfo(
        val actorRef: ActorRef
    )

    companion object {
        val CHANNEL_ACTOR: AttributeKey<ActorRef> = AttributeKey.valueOf("CHANNEL_ACTOR")
    }

    override fun channelActive(ctx: ChannelHandlerContext) {
        val channel = ctx.channel()
        val channelActor = gate.actorOf(ChannelActor.props(ctx))
        channel.attr(CHANNEL_ACTOR).set(channelActor)

        logInfo { "channelActive 绑定actor" }
    }

    override fun channelRead0(ctx: ChannelHandlerContext, msg: GameReq) {
        val channelActor = ctx.channel().attr(CHANNEL_ACTOR).get()
        if (channelActor != null) {
            channelActor.tell(msg, ActorRef.noSender())
            logInfo { "channelRead0 msg ==> channelActor" }
        }
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已收到消息，并给你发送一个问号?", CharsetUtil.UTF_8))
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        println(cause.message)
        ctx.close()
    }

    /*override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
    }

    */


}
