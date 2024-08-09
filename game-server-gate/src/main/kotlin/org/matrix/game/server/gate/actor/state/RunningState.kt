package org.matrix.game.server.gate.actor.state

import com.google.protobuf.MessageLite
import org.matrix.game.common.akka.ClientMessage2Home
import org.matrix.game.common.log.logInfo
import org.matrix.game.proto.c2s.GameReq
import org.matrix.game.server.gate.actor.ChannelActor
import org.matrix.game.server.gate.gate

class RunningState(actor: ChannelActor) : BaseState(actor) {

    override fun handleGameReq(msg: GameReq) {
        logInfo { "收到消息：playerId=${actor.playerId} [${msg.payloadCase}]" }
        val payload = msg.getField(GameReq.getDescriptor().findFieldByNumber(msg.payloadCase.number)) as MessageLite
        gate.tellHome(ClientMessage2Home(actor.playerId, payload.toByteArray()), actor.self)
    }
}