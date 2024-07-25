package org.matrix.game.common.component

import com.typesafe.config.ConfigFactory
import org.matrix.game.common.base.Process


class CompCfg(
    val process: Process
) : AbstractComponent() {

    init {
    }

    override fun close() {
    }
}