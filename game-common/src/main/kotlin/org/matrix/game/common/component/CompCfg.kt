package org.matrix.game.common.component

import org.matrix.game.common.base.BaseProcess


class CompCfg private constructor(
    val process: BaseProcess
) : AbstractComponent() {

    init {
    }

    override fun close() {
    }
}