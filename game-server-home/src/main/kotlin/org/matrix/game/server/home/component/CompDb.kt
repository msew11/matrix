package org.matrix.game.server.home.component

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.AbstractComponent

class CompDb private constructor(
    compCfg4Home: CompCfg4Home
) : AbstractComponent() {

    var sessionFactory: SessionFactory

    companion object {
        fun reg(process: BaseProcess, compCfg4Home: CompCfg4Home): BaseProcess.CompAccess<CompDb> =
            process.regComponent { CompDb(compCfg4Home) }
    }

    init {
        val url = "jdbc:mysql://${compCfg4Home.host}/${compCfg4Home.dbName}?createDatabaseIfNotExist=true"
        val hibernateCfg = Configuration().configure("hibernate.cfg.xml")
        hibernateCfg.setProperty("hibernate.connection.url", url)
        hibernateCfg.setProperty("hibernate.connection.username", compCfg4Home.username)
        hibernateCfg.setProperty("hibernate.connection.password", compCfg4Home.password)

        sessionFactory = hibernateCfg.buildSessionFactory()
    }

    override fun close() {

    }
}