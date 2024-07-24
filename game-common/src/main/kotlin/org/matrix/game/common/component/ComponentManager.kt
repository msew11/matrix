package org.matrix.game.common.component

class ComponentManager: AbstractComponent() {

    fun <T : AbstractComponent> regComponent(component: T): T {
        dependOn(component)
        return component
    }

    override fun doInit() {
    }

    override fun doClose() {
    }
}