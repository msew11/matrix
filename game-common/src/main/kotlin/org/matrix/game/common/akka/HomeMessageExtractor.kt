package org.matrix.game.common.akka

import akka.cluster.sharding.ShardRegion
import org.matrix.game.proto.home.HomeMessage

class HomeMessageExtractor(private val numberOfShards: Int) : ShardRegion.MessageExtractor {
    override fun entityId(message: Any): String {
        return when (message) {
            is ClientReq2Home -> message.playerId.toString()
            is HomeMessage -> message.playerId.toString()
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun entityMessage(message: Any): Any = message

    override fun shardId(message: Any): String {
        return when (message) {
            is ClientReq2Home -> Math.ceilMod(message.playerId, numberOfShards).toString()
            is HomeMessage -> Math.ceilMod(message.playerId, numberOfShards).toString()
            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}

