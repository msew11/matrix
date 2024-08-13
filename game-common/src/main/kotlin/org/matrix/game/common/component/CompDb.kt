package org.matrix.game.common.component

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.matrix.game.common.base.BaseProcess

class CompDb private constructor(
    val process: BaseProcess,
) : AbstractComponent() {

    lateinit var host: String
    lateinit var dbName: String
    lateinit var username: String
    lateinit var password: String

    lateinit var sessionFactory: SessionFactory

    companion object {
        fun reg(process: BaseProcess): BaseProcess.CompAccess<CompDb> = process.regComponent { CompDb(process) }
    }

    private fun hibernateSessionFactory(): SessionFactory {
        val url = "jdbc:mysql://${host}/${dbName}?createDatabaseIfNotExist=true"
        val hibernateCfg = Configuration().configure("hibernate.cfg.xml")
        hibernateCfg.setProperty("hibernate.connection.url", url)
        hibernateCfg.setProperty("hibernate.connection.username", username)
        hibernateCfg.setProperty("hibernate.connection.password", password)

        return hibernateCfg.buildSessionFactory()
    }

    override fun loadConfig() {
        host = process.config.getString("game.db.host")
        dbName = process.config.getString("game.db.name")
        username = process.config.getString("game.db.username")
        password = process.config.getString("game.db.password")
    }

    override fun init() {
        sessionFactory = hibernateSessionFactory()
    }

    override fun close() {

    }
}