package org.matrix.game.server.home

import org.matrix.game.common.base.Process
import org.matrix.game.common.constg.EProcessType

class Home : Process(EProcessType.HOME) {

    override fun prepare() {
    }

}

lateinit var home: Home