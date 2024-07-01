package org.matrix.game.server.gateway.component

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.protobuf.ProtobufDecoder
import io.netty.handler.codec.protobuf.ProtobufEncoder
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender
import org.matrix.game.common.component.IComponent
import org.matrix.game.common.network.IServer
import org.matrix.game.common.network.netty.NettyServer
import org.matrix.game.proto.c2s.GameReq
import org.matrix.game.server.gateway.network.MyServerHandler

class CompNetwork : IComponent {

    private lateinit var server: IServer

    override fun isInitialized(): Boolean {
        return this::server.isInitialized
    }

    override fun doInit() {
        val server = NettyServer(
            6666,
            object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    // 解码
                    ch.pipeline().addLast(ProtobufVarint32FrameDecoder())
                    ch.pipeline().addLast(ProtobufDecoder(GameReq.getDefaultInstance()))
                    // 编码
                    ch.pipeline().addLast(ProtobufEncoder())
                    ch.pipeline().addLast(ProtobufVarint32LengthFieldPrepender())

                    ch.pipeline().addLast(MyServerHandler())
                }
            }
        )
        server.start()

        this.server = server
    }

    override fun doClose() {
        server.shutdown()
    }
}