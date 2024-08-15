package org.matrix.game.server.home.component

import com.google.protobuf.Descriptors
import com.google.protobuf.Descriptors.FieldDescriptor
import com.google.protobuf.Message
import org.matrix.game.common.base.BaseProcess
import org.matrix.game.common.component.AbstractComponent
import org.matrix.game.core.log.logger
import org.matrix.game.proto.client.ClientResp
import org.matrix.game.server.home.handler.BaseHandler
import org.reflections.Reflections
import java.lang.reflect.ParameterizedType

class CompHomeMessage private constructor(process: BaseProcess) : AbstractComponent() {

    val handlersMap = hashMapOf<String, BaseHandler<*, *>>()

    val respDescriptorMap = hashMapOf<Class<*>, FieldDescriptor>()

    companion object {
        val logger by logger()
        fun reg(process: BaseProcess): BaseProcess.CompAccess<CompHomeMessage> =
            process.regComponent { CompHomeMessage(process) }
    }

    init {


        val clientResp = ClientResp.getDefaultInstance()
        ClientResp.PayloadCase.entries.filter { it.number != 0 }.forEach {
            val fieldDescriptor = ClientResp.getDescriptor().findFieldByNumber(it.number)
            respDescriptorMap[clientResp.getField(fieldDescriptor).javaClass] = fieldDescriptor
        }

        val subClazzList = Reflections(process.defaultScanPackage)
            .getSubTypesOf(BaseHandler::class.java)
        subClazzList.forEach {
            val handler = it.getConstructor().newInstance()

            // handler泛型 -> proto Descriptor
            val genericSuperclass = it.genericSuperclass as ParameterizedType

            @Suppress("unchecked_cast")
            val reqClass = genericSuperclass.actualTypeArguments[0] as Class<Message>
            val method = reqClass.getDeclaredMethod("getDescriptor")
            val descriptor = method.invoke(null) as Descriptors.Descriptor

            @Suppress("unchecked_cast")
            val respClass = genericSuperclass.actualTypeArguments[1] as Class<Message>

            // 绑定handler的请求和返回类型
            bindHandler(handler, reqClass, respClass)

            logger.info { "注册消息：${descriptor.fullName} -> ${handler.javaClass.simpleName}" }
            handlersMap[descriptor.fullName] = handler
        }
    }

    private fun bindHandler(handler: BaseHandler<*, *>, reqClass: Class<Message>, respClass: Class<Message>) {

        val fieldDescriptor = respDescriptorMap[respClass]
            ?: throw RuntimeException("${handler.javaClass.simpleName} 泛型RESP不在ClientResp协议消息中")

        handler.msgType = reqClass
        handler.respFieldDescriptor = fieldDescriptor
    }

    fun fetchHandler(msgName: String): BaseHandler<Message, Message>? {
        @Suppress("unchecked_cast")
        return handlersMap[msgName] as BaseHandler<Message, Message>?
    }

    override fun close() {
    }
}