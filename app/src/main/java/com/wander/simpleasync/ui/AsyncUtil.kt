package com.wander.simpleasync.ui

import android.util.Log
import com.wander.simpleasync.ListenableExecutors
import com.wander.simpleasync.ListenableFutureTask
import com.wander.simpleasync.Observer
import java.util.concurrent.Callable

/**
 * author wangdou
 * date 2018/8/11
 *
 */
object AsyncUtil {
    val tag = "AsyncUtil"
    private val singleExecutorService = ListenableExecutors.newSingleThreadExecutor()

    fun executorSingle() {
        Log.e(tag, "start")
        val future = singleExecutorService.submit(Callable<String> {

            Thread.sleep(2000)
            return@Callable "sleep"
        }) as ListenableFutureTask
        future.observer = object : Observer<String> {
            override fun onNext(t: String) {
                Log.e(tag, "onNext:$t")
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
                Log.e(tag, "onComplete")
            }
        }

    }

}