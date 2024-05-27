package org.matrix.game.server.gateway

import org.matrix.game.common.manager.CoreManager
import org.matrix.game.server.gateway.manager.ServerNodeManager
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener

@SpringBootApplication
class GameServerGatewayApplication

fun main(args: Array<String>) {
    val application = SpringApplication(GameServerGatewayApplication::class.java)
    application.addListeners(ApplicationReadyEventListener())
    application.run(*args)
}

class ApplicationReadyEventListener : ApplicationListener<ApplicationReadyEvent> {
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val coreManager = CoreManager.getInstance()
        coreManager.regManager(ServerNodeManager())
        coreManager.init()
    }
}
