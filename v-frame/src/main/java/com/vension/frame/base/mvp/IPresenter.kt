package com.vension.frame.base.mvp

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/11 17:57
 * 描  述：IPresenter--基类
 * ========================================================
 */

interface IPresenter<in V : IBaseView> {

     fun attachView(view: V)

     fun detachView()

}