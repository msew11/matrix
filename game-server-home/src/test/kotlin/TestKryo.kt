import com.google.common.hash.Funnel
import com.google.common.hash.HashFunction
import com.google.common.hash.Hashing
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.matrix.game.core.db.AbstractEntityWrapper
import org.matrix.game.core.db.IEntity
import org.matrix.game.core.json.toJson
import org.matrix.game.core.util.KryoUtil
import org.matrix.game.server.home.dc.genericType
import org.matrix.game.server.home.dc.simpleType
import org.matrix.game.server.home.dc.structType
import org.matrix.game.server.home.entity.CharacterEntity
import org.matrix.game.server.home.entity.TestClass
import org.reflections.ReflectionUtils
import org.reflections.Reflections
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType
import java.util.*
import kotlin.reflect.full.isSubclassOf
import kotlin.test.BeforeTest
import kotlin.test.Test

interface EntityWrapper4T<E : IEntity> {
    fun wrap(e: E)
}

abstract class AbstractEntityWrapper4T<E : IEntity> : EntityWrapper4T<E> {
    abstract var e: E

    final override fun wrap(e: E) {
        this.e = e
    }
}

class Character4T(
) : AbstractEntityWrapper4T<CharacterEntity>() {
    override lateinit var e: CharacterEntity
}

class TestKryo {

    val fullCheckHashFunction: HashFunction = Hashing.goodFastHash(128)

    lateinit var config: Config
    lateinit var scanPack: String

    @BeforeTest
    fun before() {
        this.config = ConfigFactory.load("home.conf")
        val scanPack = config.getString("game.scan_pack")
        assert(scanPack != null)
        this.scanPack = scanPack
    }

    @Test
    fun test() {

        val e = CharacterEntity(100001, "张三", """[{"a":"a","b":1}]""")
        //val e = CharacterEntity(100001, "张三", """[]""")
        val character = Character4T().apply {
            wrap(e)
        }
    }

    @Test
    fun test2() {
        val reflections = Reflections(scanPack)
        val scanResult = reflections.getSubTypesOf(AbstractEntityWrapper::class.java)
            .filter { !Modifier.isAbstract(it.modifiers) } // 过滤掉抽象类
    }

    class SuperStruct(
        val v1: Int = 0,
        val v2: Long = 0L,
        val v3: String = "",
        val v4: Boolean = false,
        val v5: LinkedList<Long> = LinkedList(),
        val v6: TestClass = TestClass(),
    )

    private fun <T : Any> genInstance(clazz: Class<T>): T {
        val instance = clazz.getDeclaredConstructor().newInstance()
        fillStructType(instance)
        return instance
    }

    private fun fillStructType(instance: Any) {
        for (f in ReflectionUtils.getAllFields(instance.javaClass)) {
            fillField(instance, f)
        }
    }

    private fun fillField(instance: Any, field: Field) {
        field.isAccessible = true
        val fieldValue = field.get(instance)
        if (fieldValue == null) {
            error("数据字段为空 ${instance.javaClass.name}::${field.name}")
        }

        val ktType = field.type.kotlin
        if (ktType in simpleType) {
            return
        } else if (ktType in genericType) {
            fillParameterizedType(fieldValue, field)
        } else if (ktType in structType) {
            fillStructType(fieldValue)
        } else {
            error("不支持的类型")
        }
    }

    private fun fillParameterizedType(fieldValue: Any, field: Field) {
        val parameterizedType = field.genericType as ParameterizedType
        if (field.type.kotlin.isSubclassOf(List::class)) {
            parameterizedType.actualTypeArguments[0]
        }

        println("aa")
    }

    @Test
    fun test03() {
        val instance = genInstance(SuperStruct::class.java)
        println(toJson(instance))

        val kryoUtil = KryoUtil { kryo ->
            kryo.register(SuperStruct::class.java)
            kryo.register(LinkedList::class.java)
            kryo.register(TestClass::class.java)
        }

        val funnel = Funnel<Any> { from, into ->
            val kb = kryoUtil.serialize(from)
            into.putBytes(kb.bytes, 0, kb.dataLength)
        }

        val hashCode = fullCheckHashFunction.hashObject(instance, funnel)

        println(hashCode.hashCode())
    }


    @Test
    fun testNoArg() {
        val constructor = SuperStruct::class.java.getDeclaredConstructor()
        val instance = constructor.newInstance()
        println(toJson(instance))
    }
}