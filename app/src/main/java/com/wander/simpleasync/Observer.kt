package com.wander.simpleasync

/**
 * author wangdou
 * date 2018/8/12
 *
 */
interface Observer<T> {

    fun onNext(t: T)

    fun onError(e: Throwable)

    fun onComplete()

}