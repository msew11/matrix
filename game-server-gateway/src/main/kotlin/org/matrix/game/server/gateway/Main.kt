package org.matrix.game.server.gateway

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class GameServerGatewayApplication

fun main(args: Array<String>) {
    val application = SpringApplication(GameServerGatewayApplication::class.java)
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
