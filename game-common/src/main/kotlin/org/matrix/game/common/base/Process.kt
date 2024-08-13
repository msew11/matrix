package org.matrix.game.common.base

import org.matrix.game.common.component.AbstractComponent
import org.matrix.game.common.constg.ProcessType
import org.matrix.game.core.log.logger
import kotlin.system.exitProcess

abstract class Process(val processType: ProcessType) {

    companion object {
        val logger by logger()
    }

    private val components: MutableList<AbstractComponent> = ArrayList()
    private val holdProcessor: HoldProcessor = HoldProcessor()

    abstract fun prepare()

    open fun boot() {
        try {
            prepare()
            holdProcessor.startAwait()
            logger.info { "${processType.name} started" }
        } catch (e: Exception) {
            e.printStackTrace()
            exitProcess(1)
        }
    }

    open fun shutdown() {
        try {
            logger.info { "${processType.name} stop..." }
            components.reversed().forEach {
                it.close()
                logger.info { ">>> ${it.javaClass.simpleName} stopped" }
            }
            logger.info { "${processType.name} stopped" }
            holdProcessor.stopAwait()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun <T : AbstractComponent> regComponent(component: T): T {
        components.addLast(component)
        return component
    }
}