package org.matrix.game.common.base

import java.util.concurrent.TimeUnit

class HoldProcessor {
    @Volatile
    private var stopAwait: Boolean = false
    @Volatile
    private var awaitThread: Thread? = null

    fun startAwait() {
        val awaitThread = Thread(this::await, "hold-process-thread")
        awaitThread.setContextClassLoader(javaClass.classLoader)
        awaitThread.isDaemon = false
        awaitThread.start()
    }

    fun stopAwait() {
        stopAwait = true
        val t = awaitThread
        if (t != null) {
            t.interrupt()
            try {
                t.join(1000)
            } catch (e: InterruptedException) {
                // Ignored
            }
        }
    }

    fun await() {
        try {
            awaitThread = Thread.currentThread()
            while (!stopAwait) {
                try {
                    TimeUnit.SECONDS.sleep(10)
                } catch (e: InterruptedException) {

                }
            }
        } finally {
            awaitThread = null
        }
    }
}