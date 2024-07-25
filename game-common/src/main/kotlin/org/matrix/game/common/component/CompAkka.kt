package org.matrix.game.common.component

import akka.actor.ActorSystem
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.matrix.game.common.base.Process

/**
 * @see <a href="https://doc.akka.io/docs/akka/current/cluster-usage.html#cluster-api-extension">akka集群</a>
 * @see <a href="https://doc.akka.io/docs/akka/current/cluster-usage.html#joining-to-seed-nodes">加入种子节点</a>
 * @see <a href="https://doc.akka.io/docs/akka/current/cluster-usage.html#configuration">配置</a>
 */
class CompAkka(
    val process: Process,
    val akkaHost: String,
    val akkaPort: Int,
    val seedNodes: List<String>
) : AbstractComponent() {

    val loadCfg: Config
    val actorSystem: ActorSystem

    init {
        val actorSystemName = "MATRIX"

        loadCfg = ConfigFactory.load("${process.processType.name}.conf")

        val configMap = mutableMapOf(
            "akka.remote.artery.canonical.hostname" to akkaHost,
            "akka.remote.artery.canonical.port" to akkaPort,
            "akka.cluster.seed-nodes" to seedNodes.map {
                "akka://${actorSystemName}@${it}"
            },
            "akka.cluster.roles" to listOf(process.processType.name) ,
            "akka.cluster.downing-provider-class" to "akka.cluster.sbr.SplitBrainResolverProvider",
        )
        val config = ConfigFactory.parseMap(configMap).withFallback(loadCfg)
        actorSystem = ActorSystem.create(actorSystemName, config)
    }

    override fun close() {
    }
}