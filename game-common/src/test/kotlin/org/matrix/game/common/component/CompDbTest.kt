package org.matrix.game.common.component

import org.matrix.game.common.entity.Character
import kotlin.test.Test

class CompDbTest {
    @Test
    fun test() {
        val compDb = CompDb(
            "localhost:3306",
            "game_matrix",
            "root",
            "123456"
        )

        val sessionFactory = compDb.sessionFactory
        val session = sessionFactory.openSession()
        val transaction = session.beginTransaction()

        val character = Character()

        session.save(character)

        transaction.commit()
    }
}