package org.matrix.game.server.home.component

import com.google.protobuf.Descriptors
import com.google.protobuf.Message
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.AbstractComponent
import org.matrix.game.core.log.logger
import org.matrix.game.server.home.handler.BaseHandler
import org.reflections.Reflections
import java.lang.reflect.ParameterizedType

class CompHomeMessage private constructor() : AbstractComponent() {

    val handlersMap = hashMapOf<String, BaseHandler<*>>()

    companion object {
        val logger by logger()
        fun reg(process: BaseProcess): BaseProcess.CompAccess<CompHomeMessage> =
            process.regComponent { CompHomeMessage() }
    }

    init {
        val subClazzList = Reflections("org.matrix.game.server.home")
            .getSubTypesOf(BaseHandler::class.java)
        subClazzList.forEach {
            val genericSuperclass = it.genericSuperclass as ParameterizedType

            @Suppress("unchecked_cast")
            val argumentClass = genericSuperclass.actualTypeArguments.first() as Class<Message>
            val method = argumentClass.getDeclaredMethod("getDescriptor")
            val descriptor = method.invoke(null) as Descriptors.Descriptor
            val handler = it.getConstructor().newInstance()
            handler.msgType = argumentClass
            logger.info { "注册消息：${argumentClass.simpleName}" }
            handlersMap[descriptor.fullName] = handler
        }
    }

    fun fetchHandler(msgName: String): BaseHandler<Message>? {
        @Suppress("unchecked_cast")
        return handlersMap[msgName] as BaseHandler<Message>?
    }

    override fun close() {
    }
}