package org.matrix.game.server.home.component

import akka.actor.ActorSystem
import akka.cluster.sharding.ClusterSharding
import akka.cluster.sharding.ClusterShardingSettings
import org.matrix.game.common.akka.HomeMessageExtractor
import org.matrix.game.common.base.Process
import org.matrix.game.common.component.AbstractComponent
import org.matrix.game.common.component.CompAkka
import org.matrix.game.common.constg.AkkaShardType
import org.matrix.game.server.home.actor.PlayerActor

/**
 * @see <a href="https://doc.akka.io/docs/akka/current/cluster-usage.html#node-roles">节点角色</a>
 * @see <a href="https://doc.akka.io/docs/akka/current/cluster-sharding.html">集群分片</a>
 */
class CompAkka4Home(
    val process: Process,
    val compAkka: CompAkka
) : AbstractComponent() {

    val actorSystem: ActorSystem = compAkka.actorSystem

    init {
        val settings = ClusterShardingSettings.create(actorSystem)

        ClusterSharding.get(actorSystem)
            .start(
                AkkaShardType.player.name,
                PlayerActor.props(),
                settings,
                HomeMessageExtractor()
            )
    }

    override fun close() {
    }
}