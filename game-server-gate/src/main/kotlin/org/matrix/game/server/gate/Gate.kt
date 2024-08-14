package org.matrix.game.server.gate

import akka.actor.ActorRef
import akka.actor.Props
import com.google.protobuf.Message
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.CompAkka
import org.matrix.game.common.constg.ProcessType
import org.matrix.game.server.gate.component.CompAkka4Gate
import org.matrix.game.server.gate.component.CompCfg4Gate
import org.matrix.game.server.gate.component.CompNetwork

class Gate : BaseProcess(ProcessType.gate) {

    private lateinit var compCfg4Gate: CompCfg4Gate
    private lateinit var compAkka: CompAkka
    private lateinit var compAkka4Gate: CompAkka4Gate
    private lateinit var compNetwork: CompNetwork

    override fun prepare() {
        compCfg4Gate = CompCfg4Gate.reg(this).access()
        compAkka = CompAkka.reg(this, compCfg4Gate).access()
        compAkka4Gate = CompAkka4Gate.reg(this, compAkka).access()
        compNetwork = CompNetwork.reg(this, compCfg4Gate).access()
    }

    fun actorOf(props: Props): ActorRef {
        return compAkka.actorSystem.actorOf(props)
    }

    fun tellHome(msg: Message, sender: ActorRef) {
        compAkka4Gate.homeShardProxy.tell(msg, sender)
    }

}

var gate: Gate = Gate()