package org.matrix.game.server.home.handler

import org.matrix.game.proto.client.DoSomeAction

@ProtoHandler
class DoSomeActionHandler : BaseHandler<DoSomeAction>() {
    override fun deal(msg: DoSomeAction) {
        println("xixihaha")
    }
}