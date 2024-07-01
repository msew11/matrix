package org.matrix.game.common.component

class ComponentManager {

    private val allComponents = ArrayList<IComponent>()

    fun init() {
        for (component in allComponents) {
            component.init()
        }
    }

    fun close() {
        for (component in allComponents) {
            component.close()
        }
    }

    fun <T : IComponent> regComponent(component: T): T {
        allComponents.add(component)
        return component
    }
}