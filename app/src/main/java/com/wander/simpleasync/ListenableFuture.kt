package com.wander.simpleasync

import java.util.concurrent.Executor
import java.util.concurrent.Future

/**
 * author wangdou
 * date 2018/8/11
 *
 */
interface ListenableFuture<V> : Future<V> {
    fun addListener(var1: Runnable, var2: Executor)
}