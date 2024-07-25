package org.matix.game.client.component

import org.matrix.game.common.component.AbstractComponent
import org.matrix.game.common.network.IClient
import org.matrix.game.common.network.netty.NettyClient

class CompClient : AbstractComponent() {

    private var client: IClient

    init {
        val client = NettyClient()
        client.start()

        this.client = client
    }

    override fun close() {
        client.shutdown()
    }
}