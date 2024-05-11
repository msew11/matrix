package org.matrix.server

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.LineEncoder
import io.netty.handler.codec.string.LineSeparator
import io.netty.util.CharsetUtil


fun main() {
    MyClient().start()
}

class MyClient {
    fun start() {
        val eventExecutors = NioEventLoopGroup()

        try {
            val bootstrap = Bootstrap()
            bootstrap.group(eventExecutors)
                .channel(NioSocketChannel::class.java)
                .handler(object : ChannelInitializer<SocketChannel>(){
                    override fun initChannel(ch: SocketChannel) {
                        // ch.pipeline().addLast(LineEncoder(LineSeparator.DEFAULT, CharsetUtil.UTF_8))
                        ch.pipeline().addLast(MyClientHandler())
                    }
                })

            println("客户端准备就绪，随时可以起飞~")
            val channelFuture = bootstrap.connect("127.0.0.1", 6666).sync()
            channelFuture.channel().closeFuture().sync()
        } catch (e: Exception) {
            println(e.message)
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}