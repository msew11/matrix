package org.matrix.game.server.gate.actor.state

import com.google.protobuf.Any
import com.google.protobuf.Message
import org.matrix.game.core.log.logger
import org.matrix.game.proto.client.ClientReq
import org.matrix.game.proto.home.HomeMessage
import org.matrix.game.server.gate.actor.ChannelActor
import org.matrix.game.server.gate.gate

class RunningState(actor: ChannelActor) : BaseState(actor) {

    companion object {
        val logger by logger()
    }

    override fun handleClientReq(msg: ClientReq) {
        logger.info { "收到消息：playerId=${actor.playerId} [${msg.payloadCase}]" }
        val payload = msg.getField(ClientReq.getDescriptor().findFieldByNumber(msg.payloadCase.number)) as Message

        val homeMsg = HomeMessage.newBuilder()
            .setPlayerId(actor.playerId)
            .setMsgName(payload.descriptorForType.fullName)
            .setPayload(Any.pack(payload))
            .build()
        gate.tellHome(homeMsg, actor.self)
    }
}