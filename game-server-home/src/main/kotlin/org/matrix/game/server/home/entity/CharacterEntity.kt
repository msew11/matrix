package org.matrix.game.server.home.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.matrix.game.core.db.IEntity

@Entity
@Table(name = "h_character")
class CharacterEntity(
    @Id
    @Column(name = "id", nullable = false)
    var id: Long = 0,

    @Column(name = "name", nullable = false)
    var name: String = "",

    @Column(name = "test", nullable = false, columnDefinition = "text")
    var test: String = ""
) : IEntity