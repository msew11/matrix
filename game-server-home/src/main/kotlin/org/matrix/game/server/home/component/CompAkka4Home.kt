package org.matrix.game.server.home.component

import akka.cluster.sharding.ClusterSharding
import akka.cluster.sharding.ClusterShardingSettings
import org.matrix.game.common.akka.HomeMessageExtractor
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.AbstractComponent
import org.matrix.game.common.component.CompAkka
import org.matrix.game.common.constg.AkkaShardType
import org.matrix.game.server.home.actor.PlayerActor

/**
 * @see <a href="https://doc.akka.io/docs/akka/current/cluster-usage.html#node-roles">节点角色</a>
 * @see <a href="https://doc.akka.io/docs/akka/current/cluster-sharding.html">集群分片</a>
 */
class CompAkka4Home private constructor(
    process: BaseProcess,
    compAkka: CompAkka
) : AbstractComponent() {

    companion object {
        fun reg(process: BaseProcess, compAkka: CompAkka): BaseProcess.CompAccess<CompAkka4Home> =
            process.regComponent { CompAkka4Home(process, compAkka) }
    }

    init {
        val settings = ClusterShardingSettings.create(compAkka.actorSystem)
            .withRole(process.processType.name)

        ClusterSharding.get(compAkka.actorSystem)
            .start(
                AkkaShardType.player.name,
                PlayerActor.props(),
                settings,
                HomeMessageExtractor(1000)
            )
    }

    override fun close() {
    }
}