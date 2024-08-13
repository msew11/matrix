package org.matrix.game.server.gate

import com.typesafe.config.ConfigFactory
import jakarta.annotation.PreDestroy
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class GameServerGate

fun main(args: Array<String>) {
    val application = SpringApplication(GameServerGate::class.java)
    application.run(*args)
}

@Component
class GateRunner : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val configMap = mutableMapOf(
            "game.name" to "MATRIX",
            "game.seeds" to listOf("127.0.0.1:6551"),
            "game.gate.host" to "127.0.0.1",
            "game.gate.port" to 6551,
            "game.gate.netty.port" to 6666
        )
        val config = ConfigFactory.parseMap(configMap)

        gate.boot(config)

        Runtime.getRuntime().addShutdownHook(Thread({
        }, "shutdown-hook"))
    }

    @PreDestroy
    fun shutdown() {
        gate.shutdown()
    }
}
