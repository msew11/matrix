package org.matrix.game.server.gate.component

import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.CompCfg

class CompCfg4Gate private constructor(process: BaseProcess) : CompCfg() {

    companion object {
        fun reg(process: BaseProcess): BaseProcess.CompAccess<CompCfg4Gate> =
            process.regComponent { CompCfg4Gate(process) }
    }

    override var actorSystemName: String
    override var akkaHost: String
    override var akkaPort: Int
    override var seedNodes: MutableList<String>

    var nettyPort: Int

    init {
        actorSystemName = process.config.getString("game.name")
        akkaHost = process.config.getString("game.${process.processType.name}.host")
        akkaPort = process.config.getInt("game.${process.processType.name}.port")
        seedNodes = process.config.getStringList("game.seeds")

        nettyPort = process.config.getInt("game.${process.processType.name}.netty.port")
    }

    override fun close() {
    }
}