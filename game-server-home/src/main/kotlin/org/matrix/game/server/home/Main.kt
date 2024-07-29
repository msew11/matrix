package org.matrix.game.server.home

import jakarta.annotation.PreDestroy
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class GameServerHomeApplication

fun main(args: Array<String>) {
    val application = SpringApplication(GameServerHomeApplication::class.java)
    application.run(*args)
}

@Component
class HomeRunner : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        home.boot()
    }

    @PreDestroy
    fun shutdown() {
        home.shutdown()
    }
}
