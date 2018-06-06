package com.vension.app.mvp.contract

import com.vension.app.beans.NewsItemInfo
import com.vension.frame.base.mvp.IBaseView
import com.vension.frame.base.mvp.IPresenter

/**
 * @author ：Created by Administrator on 2018/5/21 10:35.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
interface SportNewsContract {

    interface View : IBaseView{
        fun setNewsData(userInfo: List<NewsItemInfo>)
    }


    /**
     *  "69"
     */
    interface Presenter : IPresenter<View> {
        fun getSportNews(baid : String, key : String)
    }

}