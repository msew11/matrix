package org.matrix.game.server.home

import org.matrix.game.common.base.Process
import org.matrix.game.common.component.CompAkka
import org.matrix.game.common.component.CompDb
import org.matrix.game.common.constg.ProcessType
import org.matrix.game.server.home.component.CompAkka4Home

class Home : Process(ProcessType.home) {

    lateinit var compAkka: CompAkka
    lateinit var compAkka4Home: CompAkka4Home
    lateinit var compDb: CompDb

    override fun prepare() {
        compAkka = regComponent(
            CompAkka(
                this,
                "127.0.0.1",
                6552,
                listOf("127.0.0.1:6551")
            )
        )
        compAkka4Home = regComponent(CompAkka4Home(this, compAkka))
        compDb = regComponent(
            CompDb(
                "127.0.0.1:3306",
                "game_matrix",
                "root",
                "123456"
            )
        )
    }

}

var home: Home = Home()