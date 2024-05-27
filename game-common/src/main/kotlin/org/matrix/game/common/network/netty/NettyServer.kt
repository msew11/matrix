package org.matrix.game.common.network.netty

import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import org.matrix.game.common.network.ServerNode
import org.matrix.game.common.network.netty.handler.MyServerHandler

class NettyServer : ServerNode {

    companion object {
        val log = KotlinLogging.logger {}
    }

    override fun start() {
        val bossGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()

        try {
            val bootstrap = ServerBootstrap()
            bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                // 服务器套接字的等待连接队列的大小
                .option(ChannelOption.SO_BACKLOG, 128)
                // 表示允许重复使用本地地址和端口
                .option(ChannelOption.SO_REUSEADDR, true)
                // 启用 keepalive 机制
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // `true`表示禁用 Nagle 算法，降低延迟
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel) {
                        // ch.pipeline().addLast(LineBasedFrameDecoder(1024));
                        ch.pipeline().addLast(MyServerHandler())
                    }
                })

            log.info { "java技术爱好者的服务端已经准备就绪..." }
            val channelFuture = bootstrap.bind(6666).sync()
            channelFuture.channel().closeFuture().sync()
        } catch (e: Exception) {
            println(e.message)
        } finally {
            bossGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
    }

    override fun shutdown() {

    }
}
