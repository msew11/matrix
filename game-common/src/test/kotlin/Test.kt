import org.matrix.game.proto.c2s.GameReq

fun main() {



    val a = GameReq.getDescriptor().findFieldByNumber(GameReq.PayloadCase.TEST.number)
    println(a)
    val b = GameReq.getDescriptor().findFieldByNumber(GameReq.PayloadCase.TEST2.number)
    println(b)
}