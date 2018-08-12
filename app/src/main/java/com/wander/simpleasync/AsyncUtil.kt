package com.wander.simpleasync

import android.util.Log
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.logging.Logger
import kotlin.math.log

/**
 * author wangdou
 * date 2018/8/11
 *
 */
object AsyncUtil {
    val tag = "AsyncUtil"
    private val singleExecutorService = Executors.newSingleThreadExecutor()

    fun executorSingle() {
        Log.e(tag,"start")
        val future = singleExecutorService.submit(Callable<String> {

            Thread.sleep(2000)
            return@Callable "sleep"
        })
        Log.e(tag,"threat")
        var get = future.get()
        Log.e(tag, "get result:$get")

    }

}