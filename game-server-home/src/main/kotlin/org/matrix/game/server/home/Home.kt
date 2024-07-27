package org.matrix.game.server.home

import org.matrix.game.common.base.Process
import org.matrix.game.common.component.CompAkka
import org.matrix.game.common.constg.ProcessType
import org.matrix.game.server.home.component.CompAkka4Home

class Home : Process(ProcessType.home) {

    lateinit var compAkka: CompAkka
    lateinit var compAkka4Home: CompAkka4Home

    override fun prepare() {
        compAkka = regComponent(
            CompAkka(
                this,
                "127.0.0.1",
                3552,
                listOf("127.0.0.1:3551")
            )
        )
        compAkka4Home = regComponent(CompAkka4Home(this, compAkka))
    }

}

lateinit var home: Home