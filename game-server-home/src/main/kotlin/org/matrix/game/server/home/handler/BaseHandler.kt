package org.matrix.game.server.home.handler

import akka.actor.ActorRef
import com.google.protobuf.Descriptors.FieldDescriptor
import com.google.protobuf.Message
import org.matrix.game.proto.client.ClientResp
import org.matrix.game.server.home.db.PlayerDbManager

abstract class BaseHandler<REQ : Message, RESP : Message> {

    lateinit var msgType: Class<out Message>
    lateinit var respFieldDescriptor: FieldDescriptor

    abstract fun deal(context: HandlerContext, msg: REQ)

    // 这种方式性能更好
    fun buildResp(code: Int, setter: (ClientResp.Builder) -> ClientResp.Builder): ClientResp {
        return setter(ClientResp.newBuilder().setCode(code)).build()
    }

    fun buildResp(code: Int, payload: RESP): ClientResp {
        return ClientResp.newBuilder().setCode(code).setField(respFieldDescriptor, payload).build()
    }
}

class HandlerContext(
    val dbm: PlayerDbManager,
    val sender: ActorRef,
) {
    fun send2Client(resp: ClientResp) {
        sender.tell(resp, ActorRef.noSender())
    }
}