package org.matrix.game.server.home.entity

import org.matrix.game.core.db.AbstractEntityWrapper
import org.matrix.game.core.json.toJson
import org.matrix.game.core.json.toObj
import java.io.Serializable
import java.util.*

class Character() : AbstractEntityWrapper<CharacterEntity>() {
    override lateinit var e: CharacterEntity

    var test: LinkedList<String> = LinkedList()

    override fun fetchPrimaryKey(): Serializable {
        return e.id
    }

    override fun collapse(e: CharacterEntity) {
        e.test = toJson(test)
    }

    public override fun expand(e: CharacterEntity) {
        if (e.test.isNotEmpty()) {
            test = toObj(e.test)
        }
    }
}