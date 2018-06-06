package com.hazz.kotlinmvp.rx.scheduler

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/24 16:29
 * 描  述：
 * ========================================================
 */

object SchedulerUtils {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}
