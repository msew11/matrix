import org.matrix.game.common.akka.ClientReq2Home
import kotlin.test.Test

class TestNoArg {

    @Test
    fun test() {
        val constructor = ClientReq2Home::class.java.getDeclaredConstructor()
        val instance = constructor.newInstance()
        println(instance.toString())
    }
}