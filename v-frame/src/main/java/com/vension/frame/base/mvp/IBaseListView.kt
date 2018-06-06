package com.vension.frame.base.mvp

import android.view.View

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/18 14:58
 * 描  述：IListView--列表刷新基类
 * ========================================================
 */

interface IBaseListView<T> : IBaseView {

    /**
     * 设置刷新数据
     */
     fun setRefreshData(listData: List<T>)
    /**
     * 设置刷新数据
     */
     fun setRefreshData(listData: List<T>, header: List<View>?, footer: List<View>?)

    /**
     * 设置加载更多数据
     */
    fun setMoreData(listData: List<T>)


    /**
     * 设置加载更多数据失败
     */
    fun loadMoreFail()

}