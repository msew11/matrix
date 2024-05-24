package org.matrix.game.gateway.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

fun main() {
    MyServer().start()
}

class MyServer {
    fun start() {
        val bossGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()

        try {
            val bootstrap = ServerBootstrap()
            bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel) {
                        // ch.pipeline().addLast(LineBasedFrameDecoder(1024));
                        ch.pipeline().addLast(MyServerHandler())
                    }
                })

            println("java技术爱好者的服务端已经准备就绪...")
            val channelFuture = bootstrap.bind(6666).sync()
            channelFuture.channel().closeFuture().sync()
        } catch (e: Exception) {
            println(e.message)
        } finally {
            bossGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
    }
}
