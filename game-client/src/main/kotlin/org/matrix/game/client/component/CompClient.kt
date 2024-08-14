package org.matrix.game.client.component

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.protobuf.ProtobufDecoder
import io.netty.handler.codec.protobuf.ProtobufEncoder
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender
import org.matrix.game.client.network.MyClientHandler
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.AbstractComponent
import org.matrix.game.core.network.IClient
import org.matrix.game.core.network.netty.NettyClient
import org.matrix.game.proto.client.ClientResp

class CompClient private constructor() : AbstractComponent() {

    private var client: IClient

    companion object {
        fun reg(process: BaseProcess): BaseProcess.CompAccess<CompClient> = process.regComponent { CompClient() }
    }

    init {
        val client = NettyClient("127.0.0.1", 6666, object : ChannelInitializer<SocketChannel>() {
            override fun initChannel(ch: SocketChannel) {
                // 解码
                ch.pipeline().addLast(ProtobufVarint32FrameDecoder())
                ch.pipeline().addLast(ProtobufDecoder(ClientResp.getDefaultInstance()))
                // 编码
                ch.pipeline().addLast(ProtobufVarint32LengthFieldPrepender())
                ch.pipeline().addLast(ProtobufEncoder())

                ch.pipeline().addLast(MyClientHandler())
            }
        })
        client.start()

        this.client = client
    }

    override fun close() {
        client.shutdown()
    }
}