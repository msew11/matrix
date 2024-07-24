package org.matrix.game.server.gate

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener

@SpringBootApplication
class GameServerGateApplication

fun main(args: Array<String>) {
    val application = SpringApplication(GameServerGateApplication::class.java)
    application.addListeners(ApplicationReadyEventListener())
    application.run(*args)
}

class ApplicationReadyEventListener : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        gateway = Gateway()
        gateway.boot()

        Runtime.getRuntime().addShutdownHook(Thread({
            gateway.shutdown()
        }, "shutdown-hook"))
    }
}
