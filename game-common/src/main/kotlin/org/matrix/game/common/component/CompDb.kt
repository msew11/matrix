package org.matrix.game.common.component

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.matrix.game.common.base.BaseProcess

class CompDb private constructor(
    compCfg: CompCfg4Db
) : AbstractComponent() {

    var sessionFactory: SessionFactory

    companion object {
        fun reg(process: BaseProcess, compCfg: CompCfg4Db): BaseProcess.CompAccess<CompDb> =
            process.regComponent { CompDb(compCfg) }
    }

    init {
        val url = "jdbc:mysql://${compCfg.host}/${compCfg.dbName}?createDatabaseIfNotExist=true"
        val hibernateCfg = Configuration().configure("hibernate.cfg.xml")
        hibernateCfg.setProperty("hibernate.connection.url", url)
        hibernateCfg.setProperty("hibernate.connection.username", compCfg.username)
        hibernateCfg.setProperty("hibernate.connection.password", compCfg.password)

        sessionFactory = hibernateCfg.buildSessionFactory()
    }

    override fun close() {

    }
}