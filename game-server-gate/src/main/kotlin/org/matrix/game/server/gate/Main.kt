package org.matrix.game.server.gate

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
        gate.boot()

        Runtime.getRuntime().addShutdownHook(Thread({
        }, "shutdown-hook"))
    }

    @PreDestroy
    fun shutdown() {
        gate.shutdown()
    }
}
