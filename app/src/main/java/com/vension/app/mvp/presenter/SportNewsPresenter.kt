package com.vension.app.mvp.presenter

import com.vension.app.beans.NewsItemInfo
import com.vension.app.http.ApiService
import com.vension.app.mvp.contract.SportNewsContract
import com.vension.frame.base.mvp.VBasePresenter
import com.vension.frame.http.MySubscriber
import com.vension.frame.rx.RxUtil

/**
 * @author ：Created by Administrator on 2018/5/24 09:38.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class SportNewsPresenter : VBasePresenter<SportNewsContract.View>(),SportNewsContract.Presenter {

    override fun getSportNews(baid: String, key: String) {
        checkViewAttached()
        val dis = mRetrofitHelper
                .create(ApiService::class.java)
                .getQQSportNews(baid,key)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult())
                /*数据回调*/
                .subscribeWith(object : MySubscriber<List<NewsItemInfo>>(rootView) {

                     override fun onSuccess(data: List<NewsItemInfo>) {
                        rootView?.setNewsData(data)
                    }

                })
        addSubscription(dis)
    }




}