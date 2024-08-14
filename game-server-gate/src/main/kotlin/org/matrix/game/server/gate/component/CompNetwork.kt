package org.matrix.game.server.gate.component

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.protobuf.ProtobufDecoder
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.AbstractComponent
import org.matrix.game.core.network.IServer
import org.matrix.game.core.network.netty.NettyServer
import org.matrix.game.proto.client.ClientReq
import org.matrix.game.server.gate.network.MyServerHandler

class CompNetwork private constructor(compCfg4Gate: CompCfg4Gate) :
    AbstractComponent() {

    var server: IServer

    companion object {
        fun reg(process: BaseProcess, compCfg4Gate: CompCfg4Gate): BaseProcess.CompAccess<CompNetwork> =
            process.regComponent { CompNetwork(compCfg4Gate) }
    }

    init {
        val server = NettyServer(
            compCfg4Gate.nettyPort,
            object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    // 解码
                    ch.pipeline().addLast(ProtobufVarint32FrameDecoder())
                    ch.pipeline().addLast(ProtobufDecoder(ClientReq.getDefaultInstance()))
                    // 编码
                    //ch.pipeline().addLast(ProtobufEncoder())
                    //ch.pipeline().addLast(ProtobufVarint32LengthFieldPrepender())

                    ch.pipeline().addLast(MyServerHandler())
                }
            }
        )
        server.start()

        this.server = server
    }

    override fun close() {
        server.shutdown()
    }
}