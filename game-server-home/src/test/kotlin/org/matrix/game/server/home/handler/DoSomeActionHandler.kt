package org.matrix.game.server.home.handler

import org.matrix.game.proto.client.ClientResp
import org.matrix.game.proto.client.DoSomeAction
import org.matrix.game.proto.client.DoSomeActionRt
import java.util.concurrent.CompletableFuture

@ProtoHandler
class DoSomeActionHandler : BaseHandler<DoSomeAction, DoSomeActionRt>() {
    override fun deal(msg: DoSomeAction): CompletableFuture<ClientResp> {

        val rt = DoSomeActionRt.newBuilder().setMsg("收到消息:${msg.desc}")

        return CompletableFuture.completedFuture(buildResp(200) { it.setDoSomeActionRt(rt) })
    }
}