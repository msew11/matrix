package org.matrix.game.server.home

import jakarta.annotation.PreDestroy
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class GameServerHome

fun main(args: Array<String>) {
    val application = SpringApplication(GameServerHome::class.java)
    application.run(*args)
}

@Component
class HomeRunner : ApplicationRunner {

    lateinit var home: Home

    override fun run(args: ApplicationArguments) {
        home = Home()
        home.boot()
    }

    @PreDestroy
    fun shutdown() {
        if (this::home.isInitialized) {
            home.shutdown()
        }
    }
}
