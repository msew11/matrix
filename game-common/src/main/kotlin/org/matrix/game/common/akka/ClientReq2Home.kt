package org.matrix.game.common.akka

import akka.serialization.jackson.JsonSerializable
import org.matrix.game.common.base.NoArg

@NoArg
class ClientReq2Home(
    val playerId: Long,
    val msgBin: ByteArray
) : JsonSerializable