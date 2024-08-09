package org.matrix.game.client

import jakarta.annotation.PreDestroy
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class GameClient

fun main(args: Array<String>) {
    val application = SpringApplication(GameClient::class.java)
    application.run(*args)
}

@Component
class ClientRunner : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        client = Client()
        client.boot()
    }

    @PreDestroy
    fun shutdown() {
        client.shutdown()
    }
}