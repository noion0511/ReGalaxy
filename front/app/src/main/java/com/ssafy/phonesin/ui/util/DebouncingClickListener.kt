package com.ssafy.phonesin.ui.util

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

abstract class DebouncingClickListener(private val delayMillis: Long = 1000L) : View.OnClickListener {

    private var lastClickTimes = mutableMapOf<View, Long>()

    final override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        val lastClickTime = lastClickTimes[v] ?: 0L

        if (currentTime - lastClickTime > delayMillis) {
            lastClickTimes[v] = currentTime
            onDebouncedClick(v)
        }
    }

    abstract fun onDebouncedClick(v: View)
}

fun View.setDebouncingClickListener(delayMillis: Long = 1000L, onDebouncedClick: (View) -> Unit) {
    this.setOnClickListener(object : DebouncingClickListener(delayMillis) {
        override fun onDebouncedClick(v: View) {
            onDebouncedClick(v)
        }
    })
}

fun <T> LiveData<T>.getOrAwaitValueTest(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValueTest.removeObserver(this)
        }
    }
    this.observeForever(observer)
    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }
    } finally {
        this.removeObserver(observer)
    }
    return data as T
}

