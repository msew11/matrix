package org.matrix.game.client

import org.matrix.game.client.org.matrix.game.client.component.CompClient
import org.matrix.game.common.base.Process
import org.matrix.game.common.constg.ProcessType

class Client : Process(ProcessType.client) {

    lateinit var compClient: CompClient

    override fun prepare() {
        compClient = regComponent(CompClient())
    }

}

lateinit var client: Client