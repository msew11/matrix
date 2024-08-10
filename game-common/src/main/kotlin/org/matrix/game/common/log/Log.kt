package org.matrix.game.common.log

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.slf4j.Logger
import org.slf4j.LoggerFactory

//fun <T : Any> T.logInfo(func: () -> String) {
//    KotlinLogging.logger(this.javaClass.name).info(func)
//}
//
//fun <T : Any> T.logError(e: Throwable) {
//    KotlinLogging.logger(this.javaClass.name).error(e) { "" }
//}
//
//fun <T : Any> T.logError(func: () -> String) {
//    KotlinLogging.logger(this.javaClass.name).error(func)
//}
//
//fun <T : Any> T.logError(e: Throwable, func: () -> String) {
//    KotlinLogging.logger(this.javaClass.name).error(e, func)
//}


interface GameLogger {
    val logger: KLogger

    fun debug(func: () -> String) {
        logger.debug(func)
    }

    fun info(func: () -> String) {
        logger.info(func)
    }

    fun error(e: Throwable) {
        logger.error(e) { "" }
    }

    fun error(func: () -> String) {
        logger.error(func)
    }

    fun error(e: Throwable, func: () -> String) {
        logger.error(e, func)
    }
}

/**
 *
 * @see <a href="https://stackoverflow.com/questions/34416869/idiomatic-way-of-logging-in-kotlin">logging-in-kotlin</a>
 *
 */
fun <T : Any> T.logger(): Lazy<GameLogger> {
    return lazy {
        val logger = GameLoggerDelegate(KotlinLogging.logger(this.javaClass.name.replace("\$Companion", "")))
        logger.info { "logger 初始化" }
        logger
    }
}

class GameLoggerDelegate(override val logger: KLogger): GameLogger

class GlobalLog

val globalLog: Logger = LoggerFactory.getLogger(GlobalLog::class.java)