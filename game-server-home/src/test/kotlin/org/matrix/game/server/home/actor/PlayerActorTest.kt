package org.matrix.game.server.home.actor

import org.matrix.game.proto.client.DoSomeAction
import org.matrix.game.proto.home.HomeMessage.newBuilder

class PlayerActorTest {
    //@Test
    fun test() {

        val dsa = DoSomeAction.newBuilder().setDesc("aa").build()

        val build = newBuilder().setPlayerId(1).setPayload(com.google.protobuf.Any.pack(dsa)).build()

        println(dsa.descriptorForType.fullName)
        println(build.payload.typeUrl)
        println(dsa.descriptorForType.name)
    }
}