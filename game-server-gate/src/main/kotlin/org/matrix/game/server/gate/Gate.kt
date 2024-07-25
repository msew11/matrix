package org.matrix.game.server.gate

import org.matrix.game.common.base.Process
import org.matrix.game.common.component.CompAkka
import org.matrix.game.common.constg.ProcessType
import org.matrix.game.server.gate.component.CompAkka4Gate
import org.matrix.game.server.gate.component.CompNetwork

class Gateway : Process(ProcessType.GATEWAY) {

    lateinit var compAkka: CompAkka
    lateinit var compAkka4Gate: CompAkka4Gate
    lateinit var compNetwork: CompNetwork

    override fun prepare() {
        compAkka = regComponent(
            CompAkka(
                this,
                "127.0.0.1",
                2551,
                listOf("127.0.0.1:2551")
            )
        )
        compAkka4Gate = regComponent(CompAkka4Gate(this, compAkka))
        compNetwork = regComponent(CompNetwork())
    }

}

lateinit var gate: Gateway