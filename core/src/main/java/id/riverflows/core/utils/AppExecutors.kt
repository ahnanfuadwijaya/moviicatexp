package id.riverflows.core.utils

import android.os.Handler
import android.os.Looper
import androidx.annotation.VisibleForTesting

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import javax.inject.Inject

class AppExecutors @VisibleForTesting constructor(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor,
    private val scheduled: ScheduledExecutorService
) {

    @Inject
    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(THREAD_COUNT),
        MainThreadExecutor(),
        Executors.newSingleThreadScheduledExecutor()
    )

    fun diskIO(): Executor = diskIO

    fun networkIO(): Executor = networkIO

    fun mainThread(): Executor = mainThread

    fun scheduled(): ScheduledExecutorService = scheduled

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        private const val THREAD_COUNT = 3
    }
}
