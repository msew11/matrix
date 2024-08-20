package org.matrix.game.server.home

import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.CompAkka
import org.matrix.game.common.component.CompDb
import org.matrix.game.common.constg.ProcessType
import org.matrix.game.server.home.component.CompAkka4Home
import org.matrix.game.server.home.component.CompCfg4Home
import org.matrix.game.server.home.component.CompHomeMessage

class Home : BaseProcess(ProcessType.home) {

    // lateinit var compCfg4Home: CompCfg4Home
    // lateinit var compAkka: CompAkka
    // lateinit var compAkka4Home: CompAkka4Home
    // lateinit var compDb: CompDb
    // lateinit var compHomeMessage: CompHomeMessage

    override fun prepare() {
        val compCfg4Home = CompCfg4Home.reg(this).access()
        val compAkka = CompAkka.reg(this, compCfg4Home).access()
        val compDb = CompDb.reg(this, compCfg4Home).access()
        val compHomeMessage = CompHomeMessage.reg(this).access()
        val compAkka4Home = CompAkka4Home.reg(this, compAkka, compDb, compHomeMessage).access()
    }

}