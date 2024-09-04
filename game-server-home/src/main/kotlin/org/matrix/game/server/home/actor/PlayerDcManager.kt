package org.matrix.game.server.home.actor

import akka.actor.ActorRef
import org.matrix.game.core.akka.NamedRunnable
import org.matrix.game.core.concurrent.AcsFactory
import org.matrix.game.core.db.IDao
import org.matrix.game.core.db.DataContainerManager
import org.matrix.game.core.util.KryoUtil

class PlayerDcManager(
    override val owner: PlayerActor,
    kryoUtil: KryoUtil,
    fetchDao: () -> IDao,
) : DataContainerManager(
    kryoUtil = kryoUtil,
    clock = owner.clock,
    readWorkerName = PlayerActor.WorkerName.dbRead,
    wpTickCycle = 2,
    fetchDao = fetchDao
) {
    override val ownerId: Any get() = owner.playerId

    override fun acsFactory(): AcsFactory = owner.acsFactory

    override fun exec(name: String, task: () -> Unit) {
        require(name.isNotBlank()) { "taskName is blank" }
        owner.self.tell(NamedRunnable(name, task), ActorRef.noSender())
    }

    override fun handleInitializingException(e: Throwable) {
        enterInitIfLoading()
    }

    override fun handleLoadingException(e: Throwable) {
        enterInitIfLoading()
    }

    // 从数据库初始化失败，回到INIT状态等待新消息触发重试
    private fun enterInitIfLoading() {
        // 数据库出现异常，尝试关闭这个PlayerActor，等待其他消息来重试。
        owner.passivate()
    }
}