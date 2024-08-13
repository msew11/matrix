package org.matrix.game.server.home.handler

import com.google.protobuf.Message

abstract class BaseHandler<M : Message> {

    lateinit var msgType: Class<out Message>

    abstract fun deal(msg: M)
}