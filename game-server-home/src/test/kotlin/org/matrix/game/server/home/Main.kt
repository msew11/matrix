package org.matrix.game.server.home

import com.typesafe.config.ConfigFactory
import jakarta.annotation.PreDestroy
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component
import kotlin.test.Test

@SpringBootApplication
class GameServerHome

fun main(args: Array<String>) {
    val application = SpringApplication(GameServerHome::class.java)
    application.run(*args)
}

@Component
class HomeRunner : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val configMap = mutableMapOf(
            "game.name" to "MATRIX",
            "game.seeds" to listOf("127.0.0.1:6551"),
            "game.home.host" to "127.0.0.1",
            "game.home.port" to 6552,
            "game.db.host" to "localhost:3306",
            "game.db.name" to "game_matrix",
            "game.db.username" to "root",
            "game.db.password" to "123456",
        )
        val config = ConfigFactory.parseMap(configMap)

        home.boot(config)
    }

    @PreDestroy
    fun shutdown() {
        home.shutdown()
    }
}
