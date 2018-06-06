package com.vension.frame.base.mvp

import com.vension.frame.http.RetrofitFactory
import com.vension.frame.rx.RxBusJava
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/11 18:01
 * 描  述：基于Rx的P层封装, with生命周期管理
 * ========================================================
 */

open class VBasePresenter<V : IBaseView> : IPresenter<V> {

    //使用默认的base_url 在VFrame.initHttpConfig(***)配置
    protected var mRetrofitHelper = RetrofitFactory.createRetrofit()

    var rootView : V? = null
        private set

    private var mCompositeDisposable = CompositeDisposable()

    /**
     * 与view进行关联
     */
    override fun attachView(view: V) {
        this.rootView = view
    }

    /**
     * 与view解除关联，并取消订阅
     */
    override fun detachView() {
        this.rootView = null
       //保证activity结束时取消所有正在执行的订阅
        if (mCompositeDisposable.isDisposed){
           mCompositeDisposable.clear()
        }
    }

    /**
     * 判断当前View是否存活
     */
    private val isViewAttached : Boolean
            get() = rootView != null

    fun checkViewAttached(){
       if (!isViewAttached) throw RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")
    }

    fun addSubscription(disposable: Disposable){
       mCompositeDisposable.add(disposable)
    }

    protected fun <U> addRxBusSubscribe(eventType: Class<U>, act: Consumer<U>) {
        mCompositeDisposable.add(RxBusJava.getDefault().toDefaultFlowable(eventType, act))
    }


}