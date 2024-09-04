import org.matrix.game.core.json.toJson
import org.matrix.game.core.json.toObj
import kotlin.test.Test

class TestJson {
    @Test
    fun test() {
        class TestClass(
            val a: String,
            val b: Int
        )

        val jsonStr = toJson(TestClass("a", 1))
        println(jsonStr)

        assert(true)


    }

    @Test
    fun test2() {
    }
}
