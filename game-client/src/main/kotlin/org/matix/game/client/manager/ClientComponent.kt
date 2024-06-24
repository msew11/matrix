package org.matrix.game.client.org.matix.game.client.manager

import org.matrix.game.common.component.IComponent
import org.matrix.game.common.network.IClient
import org.matrix.game.common.network.netty.NettyClient

class ClientComponent : IComponent {

    private lateinit var client: IClient

    override fun init() {
        val client = NettyClient()
        client.start()

        this.client = client


    }

    override fun close() {
        client.shutdown()
    }
}