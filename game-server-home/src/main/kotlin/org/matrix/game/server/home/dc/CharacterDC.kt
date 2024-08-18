package org.matrix.game.server.home.dc

import org.matrix.game.core.db.IDao
import org.matrix.game.server.home.entity.CharacterEntity
import org.matrix.game.server.home.entity.Character

class CharacterDC : PlayerDataContainer<CharacterEntity>() {

    lateinit var character: Character

    override fun loadFromDB(playerId: Long, dao: IDao): CharacterEntity? {
        return dao.findById(CharacterEntity::class.java, playerId)
    }

    override fun initImpl(data: CharacterEntity) {
        character = wp.recover(data) { Character() }
    }
}