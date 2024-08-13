package org.matrix.game.common.akka

import akka.cluster.sharding.ShardRegion
import akka.serialization.jackson.JsonSerializable
import org.matrix.game.common.base.NoArg

class HomeMessageExtractor(private val numberOfShards: Int) : ShardRegion.MessageExtractor {
    override fun entityId(message: Any): String {
        return when (message) {
            is ClientReq2Home -> message.playerId.toString()
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun entityMessage(message: Any): Any = message

    override fun shardId(message: Any): String {
        return when (message) {
            is ClientReq2Home -> Math.ceilMod(message.playerId, numberOfShards).toString()
            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}

@NoArg
class ClientReq2Home(
    val playerId: Long,
    val msgBin: ByteArray
) : JsonSerializable