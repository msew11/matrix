package org.matrix.game.common.component

interface IComponent {

    fun isInitialized(): Boolean

    fun init() {
        if (isInitialized()) {
            return
        }

        doInit()
    }

    fun close() {
        if (isInitialized()) {
            doClose()
        }
    }

    fun doInit()

    fun doClose()
}
