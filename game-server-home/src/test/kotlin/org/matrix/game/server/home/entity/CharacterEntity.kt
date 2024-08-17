package org.matrix.game.server.home.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "h_character")
class CharacterEntity(
    @Id
    @Column(name = "id", nullable = false)
    var id: Long = 0,

    @Column(name = "name", nullable = false)
    var name: String = ""
)