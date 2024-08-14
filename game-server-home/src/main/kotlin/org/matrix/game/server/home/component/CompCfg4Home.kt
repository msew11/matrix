package org.matrix.game.server.home.component

import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.CompCfg

class CompCfg4Home private constructor(process: BaseProcess) : CompCfg() {

    companion object {
        fun reg(process: BaseProcess): BaseProcess.CompAccess<CompCfg4Home> =
            process.regComponent { CompCfg4Home(process) }
    }

    override var actorSystemName: String
    override var akkaHost: String
    override var akkaPort: Int
    override var seedNodes: MutableList<String>

    var host: String
    var dbName: String
    var username: String
    var password: String

    init {
        actorSystemName = process.config.getString("game.name")
        akkaHost = process.config.getString("game.${process.processType.name}.host")
        akkaPort = process.config.getInt("game.${process.processType.name}.port")
        seedNodes = process.config.getStringList("game.seeds")

        host = process.config.getString("game.db.host")
        dbName = process.config.getString("game.db.name")
        username = process.config.getString("game.db.username")
        password = process.config.getString("game.db.password")
    }

    override fun close() {
    }
}