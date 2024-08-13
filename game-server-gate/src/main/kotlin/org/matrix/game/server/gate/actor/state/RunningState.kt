package org.matrix.game.server.gate.actor.state

import com.google.protobuf.MessageLite
import org.matrix.game.common.akka.ClientReq2Home
import org.matrix.game.core.log.logger
import org.matrix.game.proto.client.ClientReq
import org.matrix.game.server.gate.actor.ChannelActor
import org.matrix.game.server.gate.gate

class RunningState(actor: ChannelActor) : BaseState(actor) {

    companion object {
        val logger by logger()
    }

    override fun handleClientReq(msg: ClientReq) {
        logger.info { "收到消息：playerId=${actor.playerId} [${msg.payloadCase}]" }
        val payload = msg.getField(ClientReq.getDescriptor().findFieldByNumber(msg.payloadCase.number)) as MessageLite
        gate.tellHome(ClientReq2Home(actor.playerId, payload.toByteArray()), actor.self)
    }
}