package org.matrix.game.server.gate.actor.state

import org.matrix.game.core.log.logger
import org.matrix.game.proto.client.ClientReq
import org.matrix.game.proto.client.ClientResp
import org.matrix.game.proto.client.LoginGame
import org.matrix.game.server.gate.actor.ChannelActor

class BeforeLoginState(actor: ChannelActor) : BaseState(actor) {

    companion object {
        val logger by logger()
    }

    override fun handleClientReq(msg: ClientReq) {
        logger.info { "收到消息：[${msg.payloadCase}]" }
        when (val payload = msg.getField(ClientReq.getDescriptor().findFieldByNumber(msg.payloadCase.number))) {
            is LoginGame -> handleLoginGame(payload)
            else -> {
                logger.error { "登录完成前，不应该收到登录以外的消息：${payload::class.java}" }
            }
        }
    }

    override fun handleClientResp(msg: ClientResp) {
        logger.error { "登录完成前，不应该产生返回消息：${msg::class.java}" }
    }

    private fun handleLoginGame(payload: LoginGame) {
        actor.savePlayerId(payload.playerId)
        actor.changeState(RunningState(actor))
        // gate.tellHome(ClientMessage2Home(payload.playerId, payload.toByteArray()), actor.self)
    }
}