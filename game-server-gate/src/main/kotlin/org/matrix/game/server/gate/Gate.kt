package org.matrix.game.server.gate

import akka.actor.ActorRef
import akka.actor.Props
import com.google.protobuf.Message
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.CompAkka
import org.matrix.game.common.constg.ProcessType
import org.matrix.game.server.gate.component.CompAkka4Gate
import org.matrix.game.server.gate.component.CompNetwork

class Gate : BaseProcess(ProcessType.gate) {

    private lateinit var compAkka: CompAkka
    private lateinit var compAkka4Gate: CompAkka4Gate
    private lateinit var compNetwork: CompNetwork

    override fun prepare() {
        compAkka = CompAkka.reg(this).access()
        compAkka4Gate = CompAkka4Gate.reg(this, compAkka).access()
        compNetwork = CompNetwork.reg(this).access()
    }

    fun actorOf(props: Props): ActorRef {
        return compAkka.actorSystem.actorOf(props)
    }

    fun tellHome(msg: Message, sender: ActorRef) {
        compAkka4Gate.homeShardProxy.tell(msg, sender)
    }

}

var gate: Gate = Gate()