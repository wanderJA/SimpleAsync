package com.wander.simpleasync

import java.util.concurrent.*

/**
 * author wangdou
 * date 2018/8/12
 *
 */
object ListenableExecutors{
    /**
     * Creates a thread pool that reuses a fixed number of threads
     * operating off a shared unbounded queue.  At any point, at most
     * `nThreads` threads will be active processing tasks.
     * If additional tasks are submitted when all threads are active,
     * they will wait in the queue until a thread is available.
     * If any thread terminates due to a failure during execution
     * prior to shutdown, a new one will take its place if needed to
     * execute subsequent tasks.  The threads in the pool will exist
     * until it is explicitly [shutdown][ExecutorService.shutdown].
     *
     * @param nThreads the number of threads in the pool
     * @return the newly created thread pool
     * @throws IllegalArgumentException if `nThreads <= 0`
     */
    fun newFixedThreadPool(nThreads: Int): ExecutorService {
        return ListenableThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, LinkedBlockingQueue())
    }


    /**
     * Creates a thread pool that reuses a fixed number of threads
     * operating off a shared unbounded queue, using the provided
     * ThreadFactory to create new threads when needed.  At any point,
     * at most `nThreads` threads will be active processing
     * tasks.  If additional tasks are submitted when all threads are
     * active, they will wait in the queue until a thread is
     * available.  If any thread terminates due to a failure during
     * execution prior to shutdown, a new one will take its place if
     * needed to execute subsequent tasks.  The threads in the pool will
     * exist until it is explicitly [ shutdown][ExecutorService.shutdown].
     *
     * @param nThreads the number of threads in the pool
     * @param threadFactory the factory to use when creating new threads
     * @return the newly created thread pool
     * @throws NullPointerException if threadFactory is null
     * @throws IllegalArgumentException if `nThreads <= 0`
     */
    fun newFixedThreadPool(nThreads: Int, threadFactory: ThreadFactory): ExecutorService {
        return ListenableThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                LinkedBlockingQueue(),
                threadFactory)
    }

    /**
     * Creates an Executor that uses a single worker thread operating
     * off an unbounded queue. (Note however that if this single
     * thread terminates due to a failure during execution prior to
     * shutdown, a new one will take its place if needed to execute
     * subsequent tasks.)  Tasks are guaranteed to execute
     * sequentially, and no more than one task will be active at any
     * given time. Unlike the otherwise equivalent
     * `newFixedThreadPool(1)` the returned executor is
     * guaranteed not to be reconfigurable to use additional threads.
     *
     * @return the newly created single-threaded Executor
     */
    fun newSingleThreadExecutor(): ExecutorService {
        return FinalizableDelegatedExecutorService(ListenableThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                LinkedBlockingQueue()))
    }

    /**
     * Creates an Executor that uses a single worker thread operating
     * off an unbounded queue, and uses the provided ThreadFactory to
     * create a new thread when needed. Unlike the otherwise
     * equivalent `newFixedThreadPool(1, threadFactory)` the
     * returned executor is guaranteed not to be reconfigurable to use
     * additional threads.
     *
     * @param threadFactory the factory to use when creating new
     * threads
     *
     * @return the newly created single-threaded Executor
     * @throws NullPointerException if threadFactory is null
     */
    fun newSingleThreadExecutor(threadFactory: ThreadFactory): ExecutorService {
        return FinalizableDelegatedExecutorService(ListenableThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                LinkedBlockingQueue(),
                threadFactory))
    }

    /**
     * Creates a thread pool that creates new threads as needed, but
     * will reuse previously constructed threads when they are
     * available.  These pools will typically improve the performance
     * of programs that execute many short-lived asynchronous tasks.
     * Calls to `execute` will reuse previously constructed
     * threads if available. If no existing thread is available, a new
     * thread will be created and added to the pool. Threads that have
     * not been used for sixty seconds are terminated and removed from
     * the cache. Thus, a pool that remains idle for long enough will
     * not consume any resources. Note that pools with similar
     * properties but different details (for example, timeout parameters)
     * may be created using [ThreadPoolExecutor] constructors.
     *
     * @return the newly created thread pool
     */
    fun newCachedThreadPool(): ExecutorService {
        return ListenableThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                SynchronousQueue())
    }

    /**
     * Creates a thread pool that creates new threads as needed, but
     * will reuse previously constructed threads when they are
     * available, and uses the provided
     * ThreadFactory to create new threads when needed.
     * @param threadFactory the factory to use when creating new threads
     * @return the newly created thread pool
     * @throws NullPointerException if threadFactory is null
     */
    fun newCachedThreadPool(threadFactory: ThreadFactory): ExecutorService {
        return ListenableThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                SynchronousQueue(),
                threadFactory)
    }


    /**
     * A wrapper class that exposes only the ExecutorService methods
     * of an ExecutorService implementation.
     *
     * 为了避免调用[ThreadPoolExecutor.setCorePoolSize] [ThreadPoolExecutor.setMaximumPoolSize]
     */
    private open class DelegatedExecutorService internal constructor(// Android-added: @ReachabilitySensitive
            // Needed for FinalizableDelegatedExecutorService below.
            private val e: ExecutorService) : AbstractExecutorService() {
        override fun execute(command: Runnable) {
            e.execute(command)
        }

        override fun shutdown() {
            e.shutdown()
        }

        override fun shutdownNow(): List<Runnable> {
            return e.shutdownNow()
        }

        override fun isShutdown(): Boolean {
            return e.isShutdown
        }

        override fun isTerminated(): Boolean {
            return e.isTerminated
        }

        @Throws(InterruptedException::class)
        override fun awaitTermination(timeout: Long, unit: TimeUnit): Boolean {
            return e.awaitTermination(timeout, unit)
        }

        override fun submit(task: Runnable): Future<*> {
            return e.submit(task)
        }

        override fun <T> submit(task: Callable<T>): Future<T> {
            return e.submit(task)
        }

        override fun <T> submit(task: Runnable, result: T): Future<T> {
            return e.submit(task, result)
        }

        @Throws(InterruptedException::class)
        override fun <T> invokeAll(tasks: Collection<Callable<T>>): List<Future<T>> {
            return e.invokeAll(tasks)
        }

        @Throws(InterruptedException::class)
        override fun <T> invokeAll(tasks: Collection<Callable<T>>,
                                   timeout: Long, unit: TimeUnit): List<Future<T>> {
            return e.invokeAll(tasks, timeout, unit)
        }

        @Throws(InterruptedException::class, ExecutionException::class)
        override fun <T> invokeAny(tasks: Collection<Callable<T>>): T {
            return e.invokeAny(tasks)
        }

        @Throws(InterruptedException::class, ExecutionException::class, TimeoutException::class)
        override fun <T> invokeAny(tasks: Collection<Callable<T>>,
                                   timeout: Long, unit: TimeUnit): T {
            return e.invokeAny(tasks, timeout, unit)
        }
    }

    private class FinalizableDelegatedExecutorService internal constructor(executor: ExecutorService) : DelegatedExecutorService(executor) {
        protected fun finalize() {
            super.shutdown()
        }
    }


}