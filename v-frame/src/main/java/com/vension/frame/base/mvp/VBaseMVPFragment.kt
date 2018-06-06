package com.vension.frame.base.mvp

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.vension.frame.base.VBaseFragment
import com.vension.frame.utils.VToastUtil

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/18 14:02
 * 描  述：MVP Fragment-基类
 * ========================================================
 */

abstract class VBaseMVPFragment<V : IBaseView,P : VBasePresenter<V>> : VBaseFragment(),IBaseView{


    protected lateinit var mPresenter: P


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter.attachView(this as V)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


    /**
     *  创建Presenter
     *
     *  @return P
     */
    abstract fun createPresenter(): P

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

    override fun showToast(msg: String) {
        VToastUtil.showToast(msg)
    }

    override fun resetLogin() {
        //TODO 登录失效统一处理
        val mClass = Class.forName("com.vension.app.ui.fragments.LoginFragment")
        startProxyActivity(mClass)
    }


    override fun <X> bindAutoDispose(): AutoDisposeConverter<X> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY))
    }

}