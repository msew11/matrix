package org.matrix.game.server.gateway

import org.matrix.game.common.base.Process
import org.matrix.game.common.component.CompAkka
import org.matrix.game.common.constg.EProcessType
import org.matrix.game.server.gateway.component.CompNetwork

class Gateway : Process(EProcessType.GATEWAY) {

    lateinit var compAkka: CompAkka
    lateinit var compNetwork: CompNetwork

    override fun prepare() {
        compAkka = regComponent(CompAkka())
        compNetwork = regComponent(CompNetwork())
    }

}

lateinit var gateway: Gateway