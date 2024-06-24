package org.matrix.game.common.component

class ComponentManager: IComponent {

    private val allComponents = ArrayList<IComponent>()

    override fun init() {
        for (component in allComponents) {
            component.init()
        }
    }

    override fun close() {
        for (component in allComponents) {
            component.close()
        }
    }

    fun <T : IComponent> regComponent(component: T): T {
        allComponents.add(component)
        return component
    }
}