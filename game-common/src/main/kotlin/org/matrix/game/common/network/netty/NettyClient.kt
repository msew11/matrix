package org.matrix.game.common.network.netty

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.protobuf.ProtobufEncoder
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender
import org.matrix.game.common.log.logInfo
import org.matrix.game.common.network.IClient
import org.matrix.game.common.network.netty.handler.MyClientHandler

class NettyClient : IClient {

    override fun start() {
        val eventExecutors = NioEventLoopGroup()

        try {
            val bootstrap = Bootstrap()
            bootstrap.group(eventExecutors)
                .channel(NioSocketChannel::class.java)
                .handler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel) {
                        ch.pipeline().addLast(ProtobufVarint32LengthFieldPrepender())
                        ch.pipeline().addLast(ProtobufEncoder())
                        ch.pipeline().addLast(MyClientHandler())
                    }
                })

            logInfo { "NettyClient 启动..." }
            val channelFuture = bootstrap.connect("127.0.0.1", 6666).sync()
            channelFuture.channel().closeFuture().sync()
        } catch (e: Exception) {
            println(e.message)
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

    override fun shutdown() {

    }
}
