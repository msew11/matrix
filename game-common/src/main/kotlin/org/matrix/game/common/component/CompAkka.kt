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
    private val process: BaseProcess
) : AbstractComponent() {

    companion object {
        val logger by logger()
        fun reg(process: BaseProcess): BaseProcess.CompAccess<CompAkka> =
            process.regComponent { CompAkka(process) }
    }

    lateinit var actorSystemName: String
    lateinit var akkaHost: String
    var akkaPort: Int = 0
    lateinit var seedNodes: List<String>

    lateinit var loadCfg: Config
    lateinit var actorSystem: ActorSystem

    override fun loadConfig() {
        actorSystemName = process.config.getString("game.name")
        akkaHost = process.config.getString("game.${process.processType.name}.host")
        akkaPort = process.config.getInt("game.${process.processType.name}.port")
        seedNodes = process.config.getStringList("game.seeds")
    }

    override fun init() {
        loadCfg = ConfigFactory.load("akka-${process.processType.name}.conf")

        val configMap = mutableMapOf(
            "akka.remote.artery.canonical.hostname" to akkaHost,
            "akka.remote.artery.canonical.port" to akkaPort,
            "akka.cluster.seed-nodes" to seedNodes.map {
                "akka://${actorSystemName}@${it}"
            },
            "akka.cluster.roles" to listOf(process.processType.name),
            "akka.cluster.downing-provider-class" to "akka.cluster.sbr.SplitBrainResolverProvider",
        )
        val config = ConfigFactory.parseMap(configMap).withFallback(loadCfg)
        actorSystem = ActorSystem.create(actorSystemName, config)

        logger.info { "AKKA组件初始化" }
    }

    override fun close() {
    }
}