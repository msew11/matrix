package org.matrix.game.server.home.handler

import com.google.protobuf.Descriptors.FieldDescriptor
import com.google.protobuf.Message
import org.matrix.game.proto.client.ClientResp
import java.util.concurrent.CompletableFuture

abstract class BaseHandler<REQ : Message, RESP : Message> {

    lateinit var msgType: Class<out Message>
    lateinit var respFieldDescriptor: FieldDescriptor

    abstract fun deal(msg: REQ): CompletableFuture<ClientResp>

    // 这种方式性能更好
    fun buildResp(code: Int, setter: (ClientResp.Builder) -> ClientResp.Builder): ClientResp {
        return setter(ClientResp.newBuilder().setCode(code)).build()
    }

    fun buildResp(code: Int, payload: Message.Builder): ClientResp {
        return ClientResp.newBuilder().setCode(code).setField(respFieldDescriptor, payload.build()).build()
    }
}