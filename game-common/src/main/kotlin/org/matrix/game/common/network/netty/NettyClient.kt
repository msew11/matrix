package org.matrix.game.common.network.netty

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import org.matrix.game.common.log.logInfo
import org.matrix.game.common.network.IClient

class NettyClient(
    private val host: String,
    private val port: Int,
    private val initializer: ChannelInitializer<SocketChannel>
) : IClient {

    override fun start() {
        val eventExecutors = NioEventLoopGroup()

        try {
            val bootstrap = Bootstrap()
            bootstrap.group(eventExecutors)
                .channel(NioSocketChannel::class.java)
                .handler(initializer)

            logInfo { "NettyClient 启动..." }
            val channelFuture = bootstrap.connect(host, port).sync()
            channelFuture.channel().closeFuture().sync()
        } catch (e: Exception) {
            println(e.message)
        } finally {
            eventExecutors.shutdownGracefully()
        }
    }

    override fun shutdown() {

    }
}
