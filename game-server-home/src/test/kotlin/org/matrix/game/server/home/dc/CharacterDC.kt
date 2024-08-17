package org.matrix.game.server.home.dc

import org.matrix.game.core.db.CommonDao
import org.matrix.game.server.home.db.PlayerDataContainer
import org.matrix.game.server.home.entity.CharacterEntity

class CharacterDC : PlayerDataContainer<CharacterEntity>() {

    lateinit var player: CharacterEntity

    override fun loadFromDB(playerId: Long, dao: CommonDao): CharacterEntity? {
        return dao.findById(CharacterEntity::class.java, playerId)
    }

    override fun initImpl(data: CharacterEntity) {
        player = data
    }
}