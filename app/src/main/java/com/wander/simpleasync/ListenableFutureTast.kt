package com.wander.simpleasync

import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.FutureTask

/**
 * author wangdou
 * date 2018/8/11
 *
 */
class ListenableFutureTast<V>(callable: Callable<V>) : FutureTask<V>(callable), ListenableFuture<V> {
    override fun addListener(var1: Runnable, var2: Executor) {
    }
}