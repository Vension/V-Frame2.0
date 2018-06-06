package com.vension.app.mvp.presenter

import android.os.Handler
import com.vension.app.R
import com.vension.app.beans.TestBean
import com.vension.app.mvp.contract.ListTestContract
import com.vension.frame.base.mvp.VBasePresenter

/**
 * @author ：Created by Administrator on 2018/5/18 16:21.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class ListTestPresenter : VBasePresenter<ListTestContract.View<TestBean>>(), ListTestContract.Presenter<TestBean> {

    override fun getRefreshData() {
        checkViewAttached()
        rootView?.showLoading()
        Handler().postDelayed({
            rootView?.setRefreshData(getListData())
        }, 2000)
    }

    override fun getLoadMoreData(page : Int) {
        rootView?.showLoadingDialog()
        Handler().postDelayed({
            if (page >= 2){
                rootView?.dismissLoadingDialog()
                rootView?.loadMoreFail()
            }else{
                rootView?.setMoreData(getListData())
            }
        }, 2000)
//        checkViewAttached()
//        val dis = RetrofitFactory.createRetrofit(Constant.HTTP_BASE_URL)
//                .create(ApiService::class.java)
//                .doLogin("13660358989","123456","")
//                .compose(RxUtil.rxSchedulerHelper())
//                .compose(RxUtil.handleResult())
//                .subscribeWith(object : MySubscriber<UserInfo>(rootView, false) {
//
//                    override fun onSuccess(data: UserInfo) {
//                        rootView?.loadMoreFail()
//                    }
//
//                })
//        addSubscription(dis)
    }


    private fun getListData(): List<TestBean> {
        return (0..19).map { TestBean(R.mipmap.img_load_empty, "I am Title $it", "I am desc I am desc I am desc I am descI am desc $it", "2011-12-25 20:00") }
    }



}