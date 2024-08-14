package org.matrix.game.common.base

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.matrix.game.common.component.AbstractComponent
import org.matrix.game.common.constg.ProcessType
import org.matrix.game.core.log.logger
import kotlin.system.exitProcess

abstract class BaseProcess(val processType: ProcessType) {

    companion object {
        val logger by logger()
    }

    lateinit var config: Config
    // 调用者所在的包，反射会去扫描它
    lateinit var defaultScanPackage: String
    val components: MutableList<AbstractComponent> = ArrayList()
    val componentsMap: MutableMap<Class<*>, AbstractComponent> = mutableMapOf()
    private val holdProcessor: HoldProcessor = HoldProcessor()

    abstract fun prepare()

    open fun boot() {
        try {
            this.config = ConfigFactory.load("${processType.name}.conf")
            resolvePackage(Exception().stackTrace[1])
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

    private fun resolvePackage(stackTraceElement: StackTraceElement) {
        val className = stackTraceElement.className
        val clazz = Class.forName(className)
        defaultScanPackage = clazz.packageName
        logger.info { "packageName $defaultScanPackage" }
    }

    /*fun <T : AbstractComponent> regComponent(component: T): T {
        componentsMap[component.javaClass] = component
        components.addLast(component)
        return component
    }*/

    inline fun <reified T : AbstractComponent> regComponent(build: () -> T): CompAccess<T> {
        val comp = componentsMap.getOrPut(T::class.java) {
            val component = build()
            components.addLast(component)
            logger.info { "${T::class.java.simpleName} 组件初始化" }
            component
        } as T
        return CompAccess(comp)
    }

    class CompAccess<T : AbstractComponent>(private val comp: T) {
        fun access(): T = comp
    }
}