package com.vension.app.ui.fragments.agent

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.app.beans.TestBean
import com.vension.app.mvp.contract.ListTestContract
import com.vension.app.mvp.presenter.ListTestPresenter
import com.vension.app.ui.adapters.RecyListTestAdapter
import com.vension.frame.base.VBaseRefreshFragment

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/18 16:15
 * 描  述：
 * ========================================================
 */

class TestListFragment : VBaseRefreshFragment<TestBean,ListTestContract.View<TestBean>,ListTestPresenter>(),ListTestContract.View<TestBean>{

    override fun initRefreshFragment() {
    }

    override fun createCommonRecyAdapter(): BaseQuickAdapter<TestBean, BaseViewHolder> {
        return RecyListTestAdapter()
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<TestBean, BaseViewHolder>) {
    }

    override fun onTargerRequestApi(isRefresh: Boolean, page: Int, limit: Int) {
        if (isRefresh){
            mPresenter.getRefreshData()
        }else{
            mPresenter.getLoadMoreData(page)
        }
    }

    override fun createPresenter(): ListTestPresenter {
        return ListTestPresenter()
    }


}