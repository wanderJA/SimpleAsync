package com.wander.simpleasync

import java.util.concurrent.*

/**
 * author wangdou
 * date 2018/8/12
 *
 */
open class ListenableThreadPoolExecutor : ThreadPoolExecutor {

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters and default rejected execution handler.
     *
     * @param corePoolSize the number of threads to keep in the pool, even
     *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the
     *        pool
     * @param keepAliveTime when the number of threads is greater than
     *        the core, this is the maximum time that excess idle threads
     *        will wait for new tasks before terminating.
     * @param unit the time unit for the {@code keepAliveTime} argument
     * @param workQueue the queue to use for holding tasks before they are
     *        executed.  This queue will hold only the {@code Runnable}
     *        tasks submitted by the {@code execute} method.
     * @param threadFactory the factory to use when the executor
     *        creates a new thread
     * @throws IllegalArgumentException if one of the following holds:<br>
     *         {@code corePoolSize < 0}<br>
     *         {@code keepAliveTime < 0}<br>
     *         {@code maximumPoolSize <= 0}<br>
     *         {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException if {@code workQueue}
     *         or {@code threadFactory} is null
     */
    constructor(corePoolSize: Int,
                maximumPoolSize: Int,
                keepAliveTime: Long,
                unit: TimeUnit,
                workQueue: BlockingQueue<Runnable>,
                threadFactory: ThreadFactory? = Executors.defaultThreadFactory()) : super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory)


    /**
     * @param handler the handler to use when execution is blocked
     * because the thread bounds and queue capacities are reached
     * @throws IllegalArgumentException if one of the following holds:<br></br>
     * `corePoolSize < 0`<br></br>
     * `keepAliveTime < 0`<br></br>
     * `maximumPoolSize <= 0`<br></br>
     * `maximumPoolSize < corePoolSize`
     * @throws NullPointerException if `workQueue`
     * or `threadFactory` or `handler` is null
     */
    constructor(corePoolSize: Int,
                maximumPoolSize: Int,
                keepAliveTime: Long,
                unit: TimeUnit,
                workQueue: BlockingQueue<Runnable>?,
                threadFactory: ThreadFactory?,
                handler: RejectedExecutionHandler?) : super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler)

    private fun <T> newOrgTask(runnable: Runnable, value: T? = null): RunnableFuture<T> {
        return FutureTask(runnable, value)
    }

    protected fun <T> newListenableTask(runnable: Runnable, value: T? = null): ListenableFutureTask<T> {
        return ListenableFutureTask(runnable, value)
    }

    protected fun <T> newListenableTask(task: Callable<T>): ListenableFutureTask<T> {
        return ListenableFutureTask(task)
    }


    override fun <T> submit(task: Runnable, result: T?): Future<T> {
        val ftask = newListenableTask(task, result)
        execute(ftask)
        return ftask
    }

    override fun <T> submit(task: Callable<T>): Future<T> {
        val ftask = newListenableTask(task)
        execute(ftask)
        return ftask
    }


}