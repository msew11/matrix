package org.matrix.game.common.base

import org.matrix.game.common.component.ComponentManager
import org.matrix.game.common.component.AbstractComponent
import org.matrix.game.common.constg.ProcessType
import org.matrix.game.common.log.logInfo

abstract class Process(val processType: ProcessType) {

    private val componentManager: ComponentManager = ComponentManager()
    private val holdProcessor: HoldProcessor = HoldProcessor()

    abstract fun prepare()

    open fun boot() {
        prepare()
        componentManager.init()
        logInfo { "${processType.name} started" }
        holdProcessor.startAwait()
    }

    open fun shutdown() {
        logInfo { "${processType.name} stop ..." }
        componentManager.close()
        // TimeUnit.SECONDS.sleep(10)
        holdProcessor.stopAwait()
    }

    fun <T : AbstractComponent> regComponent(component: T): T {
        return componentManager.regComponent(component)
    }
}