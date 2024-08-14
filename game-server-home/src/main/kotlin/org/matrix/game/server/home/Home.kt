package org.matrix.game.server.home

import com.google.protobuf.Message
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.CompAkka
import org.matrix.game.common.constg.ProcessType
import org.matrix.game.server.home.component.CompAkka4Home
import org.matrix.game.server.home.component.CompCfg4Home
import org.matrix.game.server.home.component.CompDb
import org.matrix.game.server.home.component.CompHomeMessage
import org.matrix.game.server.home.handler.BaseHandler

class Home : BaseProcess(ProcessType.home) {

    lateinit var compCfg4Home: CompCfg4Home
    lateinit var compAkka: CompAkka
    lateinit var compAkka4Home: CompAkka4Home
    lateinit var compDb: CompDb
    lateinit var compHomeMessage: CompHomeMessage

    override fun prepare() {
        compCfg4Home = CompCfg4Home.reg(this).access()
        compAkka = CompAkka.reg(this, compCfg4Home).access()
        compAkka4Home = CompAkka4Home.reg(this, compAkka).access()
        compDb = CompDb.reg(this, compCfg4Home).access()
        compHomeMessage = CompHomeMessage.reg(this).access()
    }

    fun fetchMessageHandler(msgName: String): BaseHandler<Message, Message>? {
        return compHomeMessage.fetchHandler(msgName)
    }

}

var home: Home = Home()