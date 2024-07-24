package org.matrix.game.client

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener

@SpringBootApplication
class GameClientApplication

fun main(args: Array<String>) {
    val application = SpringApplication(GameClientApplication::class.java)
    application.addListeners(ApplicationReadyEventListener())
    application.run(*args)
}

class ApplicationReadyEventListener : ApplicationListener<ApplicationReadyEvent> {
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        client = Client()
        client.boot()
    }
}