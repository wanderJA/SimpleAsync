package com.wander.simpleasync

import android.util.Log
import java.util.concurrent.Executor

/**
 * author wangdou
 * date 2018/8/11
 *
 */
class ExcutionList {
    private var runnables: RunnableExecutorPair? = null
    private var executed: Boolean = false

    fun add(var1: Runnable, var2: Executor) {
        synchronized(this) {
            if (!this.executed) {
                this.runnables = RunnableExecutorPair(var1, var2, this.runnables)
                return
            }
        }

        //如果已经执行出结果
        executeListener(var1, var2)
    }

    fun execute() {
        var var1: RunnableExecutorPair? = null
        synchronized(this) {
            if (this.executed) {
                return
            }

            this.executed = true
            var1 = this.runnables
            this.runnables = null
        }

        var var2: RunnableExecutorPair? = null
        var var3: RunnableExecutorPair?
        while (var1 != null) {
            var3 = var1
            var1 = var1?.next
            var3?.next = var2
            var2 = var3
        }

        while (var2 != null) {
            executeListener(var2.runnable, var2.executor)
            var2 = var2.next
        }

    }

    private fun executeListener(var0: Runnable, var1: Executor) {
        try {
            var1.execute(var0)
        } catch (var5: RuntimeException) {
            val var3 = var0.toString()
            val var4 = var1.toString()
            val logBuilder = StringBuilder()
            logBuilder.append("RuntimeException while executing runnable ")
            logBuilder.append(var3).append(" with executor ").append(var4).append(var5)
            Log.e("executeListener", logBuilder.toString())
        }

    }

    private class RunnableExecutorPair internal constructor(internal val runnable: Runnable, internal val executor: Executor, internal var next: RunnableExecutorPair?)
}