package org.matrix.game.server.gate.actor.state

import org.matrix.game.common.log.logger
import org.matrix.game.proto.c2s.GameReq
import org.matrix.game.proto.c2s.LoginGame
import org.matrix.game.server.gate.actor.ChannelActor

class BeforeLoginState(actor: ChannelActor) : BaseState(actor) {

    companion object {
        val logger by logger()
    }

    override fun handleGameReq(msg: GameReq) {
        logger.info { "收到消息：[${msg.payloadCase}]" }
        when (val payload = msg.getField(GameReq.getDescriptor().findFieldByNumber(msg.payloadCase.number))) {
            is LoginGame -> handleLoginGame(payload)
            else -> {
                logger.error { "登录完成前，不应该收到登录以外的消息：${payload::class.java}" }
            }
        }
    }

    private fun handleLoginGame(payload: LoginGame) {
        actor.savePlayerId(payload.playerId)
        actor.changeState(RunningState(actor))
        // gate.tellHome(ClientMessage2Home(payload.playerId, payload.toByteArray()), actor.self)
    }
}