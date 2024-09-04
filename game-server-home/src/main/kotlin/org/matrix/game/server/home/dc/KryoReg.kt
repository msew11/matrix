package org.matrix.game.server.home.dc

import org.matrix.game.server.home.entity.TestClass
import java.util.*


val simpleType = listOf(
    Int::class,
    Long::class,
    String::class,
    Boolean::class
)

val genericType = mapOf(
    LinkedList::class to LinkedList<Any>()
)

val structType = listOf(
    TestClass::class,
)