package org.matrix.game.common.akka

import akka.cluster.sharding.ShardRegion
import com.google.protobuf.MessageLite

class HomeMessageExtractor : ShardRegion.MessageExtractor {
    override fun entityId(message: Any): String {
        return when(message) {
            is ClientMessage2Home -> message.playerId.toString()
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun entityMessage(message: Any): Any = message

    override fun shardId(message: Any): String {
        return when(message) {
            is ClientMessage2Home -> message.playerId.toString()
            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}

class ClientMessage2Home(
    val playerId: Long,
    val msg: MessageLite
)