package org.matrix.game.server.home.dc

import akka.actor.Actor
import org.matrix.game.core.db.CacheWriteProcessor
import org.matrix.game.core.db.IDao
import org.matrix.game.core.db.IDataContainer
import org.matrix.game.server.home.actor.PlayerActor
import org.matrix.game.server.home.actor.PlayerDcManager

abstract class PlayerDataContainer<DATA> : IDataContainer {

    private var initialized: Boolean = false
    lateinit var owner: PlayerActor private set
    val dcm: PlayerDcManager get() = owner.dcm
    val wp: CacheWriteProcessor get() = dcm.wp

    final override fun load(ownerId: Any, dao: IDao): Any? {
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

    abstract fun loadFromDB(playerId: Long, dao: IDao): DATA?

    abstract fun initImpl(data: DATA)
}