package org.matrix.game.client

import org.matrix.game.client.component.CompClient
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.constg.ProcessType

class Client : BaseProcess(ProcessType.client) {

    lateinit var compClient: CompClient

    override fun prepare() {
        compClient = CompClient.reg(this).access()
    }

}

lateinit var client: Client