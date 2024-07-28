package org.matrix.game.common.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "h_character")
class Character(
    @Id
    @Column(name = "id", nullable = false)
    var id: Long = 0,

    @Column(name = "name", nullable = false)
    var name: String = ""
)