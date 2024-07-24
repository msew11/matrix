package org.matix.game.client.component

import org.matrix.game.common.component.AbstractComponent
import org.matrix.game.common.network.IClient
import org.matrix.game.common.network.netty.NettyClient

class CompClient : AbstractComponent() {

    private lateinit var client: IClient

    override fun doInit() {
        val client = NettyClient()
        client.start()

        this.client = client


    }

    override fun doClose() {
        client.shutdown()
    }
}