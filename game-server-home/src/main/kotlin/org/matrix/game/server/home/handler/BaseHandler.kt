package org.matrix.game.server.home.handler

import com.google.protobuf.Message
import org.matrix.game.proto.client.ClientResp
import java.util.concurrent.CompletableFuture

abstract class BaseHandler<REQ : Message, RESP : Message> {

    lateinit var msgType: Class<out Message>

    abstract fun deal(msg: REQ): CompletableFuture<ClientResp>

    fun buildResp(code: Int, setter: (ClientResp.Builder) -> ClientResp.Builder): ClientResp {
        return setter(ClientResp.newBuilder().setCode(code)).build()
    }
}