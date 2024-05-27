package org.matrix.game.common.manager

class CoreManager private constructor(): IManager {

    companion object{
        private val instance: CoreManager = CoreManager()

        fun getInstance(): CoreManager {
            return instance
        }
    }

    private val allManagers = ArrayList<IManager>()

    override fun init() {
        for (manager in allManagers) {
            manager.init()
        }
    }

    override fun shutdown() {
        for (manager in allManagers) {
            manager.shutdown()
        }
    }

    fun regManager(manager: IManager) {
        allManagers.add(manager)
    }
}