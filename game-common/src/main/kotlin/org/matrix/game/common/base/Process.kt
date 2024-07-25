package org.matrix.game.common.base

import org.matrix.game.common.component.AbstractComponent
import org.matrix.game.common.constg.ProcessType
import org.matrix.game.common.log.logInfo

abstract class Process(val processType: ProcessType) {

    private val components: MutableList<AbstractComponent> = ArrayList()
    private val holdProcessor: HoldProcessor = HoldProcessor()

    abstract fun prepare()

    open fun boot() {
        prepare()
        logInfo { "${processType.name} started" }
        holdProcessor.startAwait()
    }

    open fun shutdown() {
        logInfo { "${processType.name} stop ..." }
        components.reversed().forEach { it.close() }
        // TimeUnit.SECONDS.sleep(10)
        holdProcessor.stopAwait()
    }

    fun <T : AbstractComponent> regComponent(component: T): T {
        components.addLast(component)
        return component
    }
}