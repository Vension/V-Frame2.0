package com.vension.app.ui.fragments

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.vension.app.Constant
import com.vension.app.R
import com.vension.app.beans.NewsItemInfo
import com.vension.app.mvp.contract.SportNewsContract
import com.vension.app.mvp.presenter.SportNewsPresenter
import com.vension.app.ui.adapters.RecyNewsAdapter
import com.vension.frame.base.mvp.VBaseMVPFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_tab_1.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/24 9:28
 * 描  述：
 * ========================================================
 */

class TabFragment_1 : VBaseMVPFragment<SportNewsContract.View,SportNewsPresenter>(),SportNewsContract.View  {

    private var mRecyNewsAdapter : RecyNewsAdapter?=null

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.leftImageButton.visibility = View.GONE
        mCommonTitleBar.centerTextView.text = "QQ-Sport"
    }

    override fun createPresenter(): SportNewsPresenter {
        return SportNewsPresenter()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_tab_1

    override fun initViewAndData(savedInstanceState: Bundle?) {
        mLayoutStatusView = mulLayout
        //初始化_RecyclerView
        recyclerView_tab1.setHasFixedSize(true)
        recyclerView_tab1.itemAnimator = DefaultItemAnimator()
        recyclerView_tab1.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        mRecyNewsAdapter = RecyNewsAdapter()
        recyclerView_tab1.adapter = mRecyNewsAdapter
    }

    override fun lazyLoadData() {
//        mPresenter.getSportNews("123456789","12365656")
        mPresenter.getSportNews("69", Constant.JUHE_API_KEY)
    }


    override fun setNewsData(data: List<NewsItemInfo>) {
        mRecyNewsAdapter?.setNewData(data)
    }


}