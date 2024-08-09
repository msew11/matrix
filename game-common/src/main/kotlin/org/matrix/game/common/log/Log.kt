package org.matrix.game.common.log

import io.github.oshai.kotlinlogging.KotlinLogging
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun <T : Any> T.logInfo(func: () -> String) {
    KotlinLogging.logger(this.javaClass.name).info(func)
}

fun <T : Any> T.logError(e: Throwable) {
    KotlinLogging.logger(this.javaClass.name).error(e) { "" }
}

fun <T : Any> T.logError(func: () -> String) {
    KotlinLogging.logger(this.javaClass.name).error(func)
}

fun <T : Any> T.logError(e: Throwable, func: () -> String) {
    KotlinLogging.logger(this.javaClass.name).error(e, func)
}

class GlobalLog

val globalLog: Logger = LoggerFactory.getLogger(GlobalLog::class.java)