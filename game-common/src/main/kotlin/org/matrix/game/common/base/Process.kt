package org.matrix.game.common.base

import org.matrix.game.common.component.ComponentManager
import org.matrix.game.common.component.IComponent
import org.matrix.game.common.log.logInfo

abstract class Process(private val name: String) {

    private val componentManager: ComponentManager = ComponentManager()
    private val holdProcessor: HoldProcessor = HoldProcessor()

    abstract fun prepare()

    open fun boot() {
        prepare()
        componentManager.init()
        logInfo { "$name STARTED" }
        holdProcessor.startAwait()
    }

    open fun shutdown() {
        logInfo { "$name STOP ..." }
        componentManager.close()
        // TimeUnit.SECONDS.sleep(10)
        holdProcessor.stopAwait()
    }

    fun <T : IComponent> regComponent(component: T): T {
        return componentManager.regComponent(component)
    }
}