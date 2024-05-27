package org.matrix.game.server.gateway.manager

import org.matrix.game.common.manager.IManager
import org.matrix.game.common.network.ServerNode
import org.matrix.game.common.network.netty.NettyServer

class ServerNodeManager: IManager {

    private lateinit var serverNode: ServerNode

    override fun init() {
        val server = NettyServer()
        server.start()

        serverNode = server
    }

    override fun shutdown() {
        serverNode.shutdown()
    }
}