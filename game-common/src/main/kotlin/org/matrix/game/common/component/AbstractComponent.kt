package org.matrix.game.common.component

abstract class AbstractComponent() {

    abstract fun loadConfig()

    abstract fun init()

    abstract fun close()
}
