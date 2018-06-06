package com.vension.frame.rx

import com.vension.frame.http.ApiException
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/24 15:54
 * 描  述：统一线程处理结果数据 针对 observable
 * ========================================================
 */


object RxHelper {


    /**
     * 统一线程处理结果数据
     *
     * @param <T>
     * @return
     */
    fun <T> rxSchedulerHelper(): ObservableTransformer<T, T> {    //compose简化线程
        return ObservableTransformer { observable ->
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }


    /**
     * 处理结果数据
     */
    fun <T> handleResult(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.flatMap {
                if (it != null) {
                    createSuccessData(it)
                } else {
                    Observable.error(ApiException(200, "服务器异常"))
                }
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 创建成功返回的数据
     */
    private fun <T> createSuccessData(t: T): Observable<T> {
        return Observable.create({
            try {
                it.onNext(t)
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        })
    }

}