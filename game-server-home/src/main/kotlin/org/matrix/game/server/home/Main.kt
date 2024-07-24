package org.matrix.game.server.home

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener

@SpringBootApplication
class GameServerHomeApplication

fun main(args: Array<String>) {
    val application = SpringApplication(GameServerHomeApplication::class.java)
    application.addListeners(ApplicationReadyEventListener())
    application.run(*args)
}

class ApplicationReadyEventListener : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        home = Home()
        home.boot()

        Runtime.getRuntime().addShutdownHook(Thread({
            home.shutdown()
        }, "shutdown-hook"))
    }
}
