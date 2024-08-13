package org.matrix.game.common.component

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.matrix.game.common.base.BaseProcess

class CompDb private constructor(
    val host: String,
    val dbName: String,
    val username: String,
    val password: String
) : AbstractComponent() {

    val sessionFactory: SessionFactory

    companion object {
        fun reg(
            process: BaseProcess,
            host: String,
            dbName: String,
            username: String,
            password: String
        ): BaseProcess.CompAccess<CompDb> = process.regComponent { CompDb(host, dbName, username, password) }
    }

    init {
        sessionFactory = hibernateSessionFactory()
    }

    private fun hibernateSessionFactory(): SessionFactory {
        val url = "jdbc:mysql://localhost:3306/game_matrix?createDatabaseIfNotExist=true"
        val hibernateCfg = Configuration().configure("hibernate.cfg.xml")
        hibernateCfg.setProperty("hibernate.connection.url", url)
        hibernateCfg.setProperty("hibernate.connection.username", username)
        hibernateCfg.setProperty("hibernate.connection.password", password)

        return hibernateCfg.buildSessionFactory()
    }

    override fun close() {

    }
}