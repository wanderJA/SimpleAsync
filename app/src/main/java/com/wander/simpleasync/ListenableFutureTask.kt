package com.wander.simpleasync

import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.FutureTask

/**
 * author wangdou
 * date 2018/8/11
 *
 */
class ListenableFutureTask<V> : FutureTask<V>, ListenableFuture<V> {
    constructor(callable: Callable<V>) : super(callable)
    constructor(runnable: Runnable, result: V?) : super(runnable, result)

    private val executionList = ExecutionList()

    fun <V> create(var0: Callable<V>): ListenableFutureTask<V> {
        return ListenableFutureTask(var0)
    }

    fun <V> create(var0: Runnable, var1: V): ListenableFutureTask<V> {
        return ListenableFutureTask(var0, var1)
    }

    override fun addListener(runnable: Runnable, executor: Executor) {
        this.executionList.add(runnable, executor)
    }

    override fun done() {
        executionList.execute()
    }

    override fun run() {
        super.run()
    }

    override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
        var cancel = super.cancel(mayInterruptIfRunning)
        if (cancel){

        }
        return cancel
    }
}