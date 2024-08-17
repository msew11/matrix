package org.matrix.game.common.component

import jakarta.persistence.Entity
import org.hibernate.cfg.Configuration
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.core.db.CommonDao
import org.matrix.game.core.db.DaoHibernate
import org.matrix.game.core.log.logger
import org.reflections.Reflections

class CompDb private constructor(
    process: BaseProcess,
    compCfg: CompCfg4Db
) : AbstractComponent() {

    val dao: CommonDao

    companion object {
        val logger by logger()
        fun reg(process: BaseProcess, compCfg: CompCfg4Db): BaseProcess.CompAccess<CompDb> =
            process.regComponent { CompDb(process, compCfg) }
    }

    init {
        val url = "jdbc:mysql://${compCfg.host}/${compCfg.dbName}?createDatabaseIfNotExist=true"
        val hibernateCfg = Configuration().configure("hibernate.cfg.xml")
        hibernateCfg.setProperty("hibernate.connection.url", url)
        hibernateCfg.setProperty("hibernate.connection.username", compCfg.username)
        hibernateCfg.setProperty("hibernate.connection.password", compCfg.password)

        val scanClass = Reflections(process.defaultScanPackage)
            .getTypesAnnotatedWith(Entity::class.java)
        scanClass.forEach {
            hibernateCfg.addAnnotatedClass(it)
            logger.info { "hibernate mapping: ${it.name}" }
        }

        dao = DaoHibernate(hibernateCfg.buildSessionFactory())
    }

    override fun close() {

    }
}