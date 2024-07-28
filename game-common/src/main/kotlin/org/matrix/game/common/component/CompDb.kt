package org.matrix.game.common.component

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.matrix.game.common.log.logInfo

class CompDb(
    val host: String,
    val dbName: String,
    val username: String,
    val password: String
): AbstractComponent() {

    val sessionFactory: SessionFactory

    init {
        val url = "jdbc:mysql://localhost:3306/game_matrix?createDatabaseIfNotExist=true"
        val hibernateCfg = Configuration().configure("hibernate.cfg.xml")
        hibernateCfg.setProperty("hibernate.connection.url", url)
        hibernateCfg.setProperty("hibernate.connection.username", username)
        hibernateCfg.setProperty("hibernate.connection.password", password)

        logInfo { "aaa" + hibernateCfg.getProperty("connection.url") }

        sessionFactory = hibernateCfg.buildSessionFactory()
    }

    override fun close() {

    }
}