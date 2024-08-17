import org.hibernate.cfg.Configuration
import org.matrix.game.core.db.DaoHibernate
import org.matrix.game.server.home.dc.CharacterDC
import org.matrix.game.server.home.entity.CharacterEntity

fun main() {
    TestDb().test()
}

class TestDb {

    fun test() {

        val compCfg = TestCfg()

        val url = "jdbc:mysql://${compCfg.host}/${compCfg.dbName}?createDatabaseIfNotExist=true"
        val hibernateCfg = Configuration().configure("hibernate.cfg.xml")
        hibernateCfg.setProperty("hibernate.connection.url", url)
        hibernateCfg.setProperty("hibernate.connection.username", compCfg.username)
        hibernateCfg.setProperty("hibernate.connection.password", compCfg.password)

        val dao = DaoHibernate(hibernateCfg.buildSessionFactory())

        //
        val container = CharacterDC::class.java.getDeclaredConstructor().newInstance()

        val characterEntity = container.load(10000001L, dao) as CharacterEntity?
        println(characterEntity?.name)
    }
}

data class TestCfg(
    val host: String = "localhost",
    val dbName: String = "game_matrix",
    val username: String = "root",
    val password: String = "123456",
)