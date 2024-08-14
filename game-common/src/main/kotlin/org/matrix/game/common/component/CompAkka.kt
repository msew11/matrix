package org.matrix.game.common.component

import akka.actor.ActorSystem
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.core.log.logger

/**
 * @see <a href="https://doc.akka.io/docs/akka/current/cluster-usage.html#cluster-api-extension">akka集群</a>
 * @see <a href="https://doc.akka.io/docs/akka/current/cluster-usage.html#joining-to-seed-nodes">加入种子节点</a>
 * @see <a href="https://doc.akka.io/docs/akka/current/cluster-usage.html#configuration">配置</a>
 */
class CompAkka private constructor(
    process: BaseProcess,
    compCfg: CompCfg
) : AbstractComponent() {

    var loadCfg: Config
    var actorSystem: ActorSystem

    companion object {
        val logger by logger()
        fun reg(process: BaseProcess, compCfg: CompCfg): BaseProcess.CompAccess<CompAkka> =
            process.regComponent { CompAkka(process, compCfg) }
    }

    init {
        loadCfg = ConfigFactory.load("akka-${process.processType.name}.conf")

        val configMap = mutableMapOf(
            "akka.remote.artery.canonical.hostname" to compCfg.akkaHost,
            "akka.remote.artery.canonical.port" to compCfg.akkaPort,
            "akka.cluster.seed-nodes" to compCfg.seedNodes.map {
                "akka://${compCfg.actorSystemName}@${it}"
            },
            "akka.cluster.roles" to listOf(process.processType.name),
            "akka.cluster.downing-provider-class" to "akka.cluster.sbr.SplitBrainResolverProvider",
        )
        val config = ConfigFactory.parseMap(configMap).withFallback(loadCfg)
        actorSystem = ActorSystem.create(compCfg.actorSystemName, config)

        logger.info { "AKKA组件初始化" }
    }

    override fun close() {
    }
}