package com.vension.frame.base.mvp

import com.uber.autodispose.AutoDisposeConverter

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/11 17:57
 * 描  述：IView--基类
 * ========================================================
 */

interface IBaseView {

    /**
     * 显示加载动画
     */
     fun showLoading()

    /**
     * 隐藏加载动画 即显示内容页
     */
    fun dismissLoading()

    /**
     * 显示加载dialog动画
     */
     fun showLoadingDialog()

    /**
     * 隐藏加载dialog动画
     */
    fun dismissLoadingDialog()


    /**
     * 显示网络错误
     */
     fun showNetError()
    /**
     * 显示请求失败
     */
     fun showError()
    /**
     * 显示空数据
     */
     fun showEmpty()

     fun showToast(msg: String)

    /**
     * 登录失效处理
     */
    fun resetLogin()

    /**
     * 绑定生命周期
     */
     fun <X> bindAutoDispose(): AutoDisposeConverter<X>

}