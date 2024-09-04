package org.matrix.game.common.component

import jakarta.persistence.Entity
import org.hibernate.cfg.Configuration
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.core.db.HibernateDao
import org.matrix.game.core.db.IDao
import org.matrix.game.core.log.logger
import org.matrix.game.core.util.KryoUtil
import org.reflections.Reflections

class CompDb private constructor(
    process: BaseProcess,
    compCfg: ICfg4Db
) : AbstractComponent() {

    var dao: IDao

    var kryoUtil: KryoUtil

    companion object {
        val logger by logger()
        fun reg(process: BaseProcess, compCfg: ICfg4Db): BaseProcess.CompAccess<CompDb> =
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

        dao = HibernateDao(hibernateCfg.buildSessionFactory())

        kryoUtil = KryoUtil { kryo ->
//            kryoRegisters.forEach { kryoCheckReg ->
//                kryoCheckReg.register(kryo)
//            }
        }
    }

    override fun close() {
        dao.close()
    }
}