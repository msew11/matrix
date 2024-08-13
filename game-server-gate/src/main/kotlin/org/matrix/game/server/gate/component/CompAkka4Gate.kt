package org.matrix.game.server.gate.component

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.cluster.sharding.ClusterSharding
import org.matrix.game.common.akka.HomeMessageExtractor
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.AbstractComponent
import org.matrix.game.common.component.CompAkka
import org.matrix.game.common.constg.AkkaShardType
import org.matrix.game.common.constg.ProcessType
import java.util.*

class CompAkka4Gate(
    val process: BaseProcess,
    val compAkka: CompAkka
) : AbstractComponent() {

    private val actorSystem: ActorSystem = compAkka.actorSystem

    val homeShardProxy: ActorRef

    companion object {
        fun reg(process: BaseProcess, compAkka: CompAkka): BaseProcess.CompAccess<CompAkka4Gate> =
            process.regComponent { CompAkka4Gate(process, compAkka) }
    }

    init {
        homeShardProxy = ClusterSharding.get(actorSystem)
            .startProxy(
                AkkaShardType.player.name,
                Optional.of(ProcessType.home.name),
                HomeMessageExtractor(1000)
            )
    }

    override fun close() {
    }
}