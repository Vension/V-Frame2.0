package com.vension.frame.base.mvp

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.v4.app.Fragment
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.vension.frame.base.VBaseActivity
import com.vension.frame.utils.VToastUtil

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/17 15:58
 * 描  述：MVP activity-基类
 * ========================================================
 */

abstract class VBaseMVPActivity<V : IBaseView,P : VBasePresenter<V>> : VBaseActivity(),IBaseView {

    protected lateinit var mPresenter: P


    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = createPresenter()
        mPresenter.attachView(this as V)
        super.onCreate(savedInstanceState)
    }

    /**
     *  创建Presenter
     *
     *  @return P
     */
    abstract fun createPresenter(): P


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }


    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun showLoadingDialog() {
        mloadingDialog.show()
    }

    override fun dismissLoadingDialog() {
        mloadingDialog.dismiss()
    }


    override fun showNetError() {
        mLayoutStatusView?.showNoNetwork()
    }

    override fun showError() {
        mLayoutStatusView?.showError()
    }

    override fun showEmpty() {
        mLayoutStatusView?.showEmpty()
    }

    override fun showToast(msg : String) {
        VToastUtil.showToast(msg)
    }

    override fun resetLogin() {
        //TODO 登录失效统一处理
        val mClass = Class.forName("com.vension.app.ui.fragments.LoginFragment")
        startProxyActivity(mClass as Class<out Fragment>)
    }

    override fun <X> bindAutoDispose(): AutoDisposeConverter<X> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY))
    }

}