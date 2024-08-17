package org.matrix.game.server.home.handler

import org.matrix.game.core.log.logger
import org.matrix.game.proto.client.DoSomeAction
import org.matrix.game.proto.client.DoSomeActionRt
import org.matrix.game.server.home.dc.CharacterDC

@ProtoHandler
class DoSomeActionHandler : BaseHandler<DoSomeAction, DoSomeActionRt>() {

    companion object {
        val logger by logger()
    }

    override fun deal(context: HandlerContext, msg: DoSomeAction) {

        val rt = DoSomeActionRt.newBuilder().setMsg("收到消息:${msg.desc}").build()
        logger.info { "收到消息:${msg.desc}" }

        context.dbm.load(CharacterDC::class.java) { characterDC ->

            logger.info { "查看角色名：${characterDC.player.name}" }

            context.send2Client(buildResp(200, rt))
        }

        // return CompletableFuture.completedFuture(buildResp(200) { it.setDoSomeActionRt(rt) })

    }
}