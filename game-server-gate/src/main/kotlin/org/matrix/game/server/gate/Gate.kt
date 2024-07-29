package org.matrix.game.server.gate

import akka.actor.ActorRef
import akka.actor.Props
import org.matrix.game.common.akka.ClientMessage2Home
import org.matrix.game.common.base.Process
import org.matrix.game.common.component.CompAkka
import org.matrix.game.common.constg.ProcessType
import org.matrix.game.server.gate.component.CompAkka4Gate
import org.matrix.game.server.gate.component.CompNetwork

class Gate : Process(ProcessType.gate) {

    private lateinit var compAkka: CompAkka
    private lateinit var compAkka4Gate: CompAkka4Gate
    private lateinit var compNetwork: CompNetwork

    override fun prepare() {
        compAkka = regComponent(
            // 显示保留的端口 netsh interface ipv4 show excludedportrange protocol=tcp
            CompAkka(
                this,
                "127.0.0.1",
                3551,
                listOf("127.0.0.1:3551")
            )
        )
        compAkka4Gate = regComponent(CompAkka4Gate(this, compAkka))
        compNetwork = regComponent(CompNetwork())
    }

    fun actorOf(props: Props): ActorRef {
        return compAkka.actorSystem.actorOf(props)
    }

    fun tellHome(msg: ClientMessage2Home, sender: ActorRef) {
        compAkka4Gate.homeShardProxy.tell(msg, sender)
    }

}

var gate: Gate = Gate()