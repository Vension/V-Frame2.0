package com.vension.app.mvp.contract

import com.vension.frame.base.mvp.IBaseListView
import com.vension.frame.base.mvp.IPresenter

/**
 * @author ：Created by Administrator on 2018/5/21 10:35.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
interface ListTestContract {
    interface View<T> : IBaseListView<T>

    interface Presenter<T> : IPresenter<View<T>> {
        fun getRefreshData()
        fun getLoadMoreData(page: Int)
    }
}