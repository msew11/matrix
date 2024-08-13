package org.matrix.game.server.home

import com.google.protobuf.Message
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.CompAkka
import org.matrix.game.common.component.CompDb
import org.matrix.game.common.constg.ProcessType
import org.matrix.game.server.home.component.CompAkka4Home
import org.matrix.game.server.home.component.CompHomeMessage
import org.matrix.game.server.home.handler.BaseHandler

class Home : BaseProcess(ProcessType.home) {

    lateinit var compAkka: CompAkka
    lateinit var compAkka4Home: CompAkka4Home
    lateinit var compDb: CompDb
    lateinit var compHomeMessage: CompHomeMessage

    override fun prepare() {
        compAkka = CompAkka.reg(
            this,
            "127.0.0.1",
            6552,
            listOf("127.0.0.1:6551")
        ).access()
        compAkka4Home = CompAkka4Home.reg(this, compAkka).access()
        compDb = CompDb.reg(
            this,
            "127.0.0.1:3306",
            "game_matrix",
            "root",
            "123456"
        ).access()
        compHomeMessage= CompHomeMessage.reg(this).access()
    }

    fun fetchMessageHandler(msgName: String): BaseHandler<Message>? {
        return compHomeMessage.fetchHandler(msgName)
    }

}

var home: Home = Home()