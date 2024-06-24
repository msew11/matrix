package org.matrix.game.server.gateway.component

import org.matrix.game.common.component.IComponent
import org.matrix.game.common.network.IServer
import org.matrix.game.common.network.netty.NettyServer

class NetworkComponent : IComponent {

    private lateinit var server: IServer

    override fun init() {
        val server = NettyServer()
        server.start()

        this.server = server
    }

    override fun close() {
        if (this::server.isInitialized) {
            server.shutdown()
        }
    }
}