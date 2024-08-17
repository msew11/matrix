package org.matrix.game.server.home.db

import akka.actor.Actor
import org.matrix.game.core.db.CommonDao
import org.matrix.game.core.db.DataContainer
import org.matrix.game.server.home.actor.PlayerActor

abstract class PlayerDataContainer<DATA> : DataContainer {

    private var initialized: Boolean = false
    lateinit var owner: PlayerActor private set

    final override fun load(ownerId: Any, dao: CommonDao): Any? {
        return loadFromDB(ownerId as Long, dao)
    }

    final override fun init(owner: Actor, data: Any?) {
        if (initialized) {
            return
        }

        this.owner = owner as PlayerActor

        @Suppress("UNCHECKED_CAST")
        if (data != null) {
            initImpl(data as DATA)
        }

        initialized = true
    }

    abstract fun loadFromDB(playerId: Long, dao: CommonDao): DATA?

    abstract fun initImpl(data: DATA)
}