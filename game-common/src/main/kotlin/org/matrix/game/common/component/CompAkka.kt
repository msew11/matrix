package org.matrix.game.common.component

import akka.actor.ActorSystem

class CompAkka(
) : IComponent {

    lateinit var actorSystem: ActorSystem

    override fun isInitialized(): Boolean {
        return this::actorSystem.isInitialized
    }

    override fun doInit() {
        actorSystem = ActorSystem.create("matrix")
    }

    override fun doClose() {
    }
}