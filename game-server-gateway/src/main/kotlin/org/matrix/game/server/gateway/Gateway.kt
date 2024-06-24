package org.matrix.game.server.gateway

import org.matrix.game.common.base.Process
import org.matrix.game.server.gateway.component.NetworkComponent

class Gateway : Process("GATEWAY") {

    private lateinit var networkComponent: NetworkComponent

    override fun prepare() {
        networkComponent = regComponent(NetworkComponent())
    }

}

lateinit var gateway: Gateway