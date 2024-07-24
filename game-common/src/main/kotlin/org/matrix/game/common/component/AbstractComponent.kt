package org.matrix.game.common.component

abstract class AbstractComponent(private val dependencies: MutableList<AbstractComponent>) {

    constructor(vararg dependencies: AbstractComponent): this(dependencies.toMutableList())

    var isInitialized: Boolean = false

    fun init() {
        if (isInitialized) {
            return
        }

        for (dependency in dependencies) {
            dependency.init()
        }

        doInit()

        isInitialized = true
    }

    fun close() {
        doClose()

        for (dependency in dependencies) {
            dependency.close()
        }
    }

    fun <T : AbstractComponent> dependOn(component: T) {
        if (!isInitialized) {
            dependencies.add(component)
        }
    }

    abstract fun doInit()

    abstract fun doClose()
}
