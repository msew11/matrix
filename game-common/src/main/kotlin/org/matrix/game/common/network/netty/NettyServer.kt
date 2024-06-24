package org.matrix.game.common.network.netty

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.protobuf.ProtobufDecoder
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder
import org.matrix.game.common.log.logError
import org.matrix.game.common.log.logInfo
import org.matrix.game.common.network.IServer
import org.matrix.game.common.network.netty.handler.MyServerHandler
import org.matrix.game.proto.HelloMatrix

class NettyServer : IServer {

    lateinit var bossGroup: NioEventLoopGroup
    lateinit var workerGroup: NioEventLoopGroup
    lateinit var channel: Channel
    lateinit var closeThread: Thread

    override fun start() {
        bossGroup = NioEventLoopGroup()
        workerGroup = NioEventLoopGroup()
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
                    ch.pipeline().addLast(ProtobufVarint32FrameDecoder());
                    ch.pipeline().addLast(ProtobufDecoder(HelloMatrix.getDefaultInstance()));
                    ch.pipeline().addLast(MyServerHandler())
                }
            })

        val bindFuture = bootstrap.bind(6666)

        closeThread = Thread({
            try {
                bindFuture.channel().closeFuture().sync()
            } catch (e: Exception) {
                logError(e)
            } finally {
                bossGroup.shutdownGracefully().sync()
                workerGroup.shutdownGracefully().sync()
            }
        }, "netty-close")
        closeThread.start()

        channel = bindFuture.sync().channel()
        logInfo { "NettyServer STARTED" }
    }

    override fun shutdown() {
        if (this::channel.isInitialized) {
            channel.close()
        }
        bossGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()

        //bossGroup.shutdownGracefully()
        //workerGroup.shutdownGracefully()
    }
}
