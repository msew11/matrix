import org.matrix.game.proto.HelloMatrix

fun main() {



    val a = HelloMatrix.getDescriptor().findFieldByNumber(HelloMatrix.PayloadCase.TEST.number)
    println(a)
    val b = HelloMatrix.getDescriptor().findFieldByNumber(HelloMatrix.PayloadCase.TEST2.number)
    println(b)
}