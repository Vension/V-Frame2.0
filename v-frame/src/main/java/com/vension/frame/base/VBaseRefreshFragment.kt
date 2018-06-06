package com.vension.frame.base

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.frame.R
import com.vension.frame.base.mvp.IBaseListView
import com.vension.frame.base.mvp.IBaseView
import com.vension.frame.base.mvp.VBaseMVPFragment
import com.vension.frame.base.mvp.VBasePresenter
import com.vension.frame.bindView
import com.vension.frame.utils.NetworkUtil
import com.vension.frame.utils.VLogUtil
import com.vension.frame.utils.VObsoleteUtil
import com.vension.frame.views.MultipleStatusView

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/18 14:39
 * 描  述：列表 Fragment-基类
 * ========================================================
 */

abstract class VBaseRefreshFragment<T,V : IBaseView,P : VBasePresenter<V>> : VBaseMVPFragment<V,P>()
        , SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,IBaseListView<T>{

    private val mSwipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.mSwipeRefreshLayout)
    private val mMultipleStatusView: MultipleStatusView by bindView(R.id.refresh_MultipleStatusView)
    private val mRefreshRecyclerView: RecyclerView by bindView(R.id.refresh_recyclerView)
    private val mFloatingActionButton: FloatingActionButton by bindView(R.id.refresh_fab_top)

    protected lateinit var mAdapter: BaseQuickAdapter<T, BaseViewHolder>
    private var page: Int = 1 //页数
    private val limit: Int = 10 //条目数

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_base_refresh
    }


    override fun initViewAndData(savedInstanceState: Bundle?) {
        mLayoutStatusView = mMultipleStatusView

        mFloatingActionButton.visibility = View.INVISIBLE
        mFloatingActionButton.setOnClickListener(this)

        //初始化刷新控件
        mSwipeRefreshLayout.setColorSchemeColors(VObsoleteUtil.getColor(R.color.app_main_thme_color))
        //设置刷新控件监听
        mSwipeRefreshLayout.setOnRefreshListener(this)
        //初始化_RecyclerView
        mRefreshRecyclerView.setHasFixedSize(true)
        mRefreshRecyclerView.layoutManager = createLayoutManager()
        mRefreshRecyclerView.itemAnimator = DefaultItemAnimator()
        //一些特殊的初始化
        initRefreshFragment()
        mAdapter = createCommonRecyAdapter()
        initAdapter(mAdapter)
    }

    private fun initAdapter(it: BaseQuickAdapter<T, BaseViewHolder>) {
        it.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
        if (hasLoadMore()) {
            it.setOnLoadMoreListener(this, mRefreshRecyclerView)
        }
        addItemClickListener(it)
        mRefreshRecyclerView.adapter = it
    }


    /**加载数据*/
    override fun lazyLoadData() {
        //自动刷新，演示效果
        VLogUtil.e("lazyLoadData")
        mSwipeRefreshLayout.isRefreshing = true
        mSwipeRefreshLayout.postDelayed( {
            onRefresh()
        },300)
    }

    /**下拉刷新数据*/
    override fun onRefresh() {
        VLogUtil.e("onRefresh")
        if (isCheckNet()){
            if (!NetworkUtil.isConnected(activity)){
                if (mLayoutStatusView != null) {
                    mLayoutStatusView?.showNoNetwork()
                }
                stopRefresh()
                return
            }
        }

        mAdapter.setEnableLoadMore(false)//这里的作用是防止下拉刷新的时候还可以上拉加载
        onTargerRequestApi(true,1,limit)
    }

    /**加载更多*/
    override fun onLoadMoreRequested() {
        //上拉加载更多/下一页
        VLogUtil.e("onLoadmore")
        mSwipeRefreshLayout.isEnabled = false //加载更多时不能同时下拉刷新
        onTargerRequestApi(false, ++page, limit)
    }


    override fun setRefreshData(listData: List<T>) {
        setRefreshData(listData,null,null)
    }

    override fun setRefreshData(listData: List<T>, header: List<View>?, footer: List<View>?) {
        VLogUtil.e("setRefreshData")
        //加载第一页数据
        mLayoutStatusView?.showContent()
        mAdapter.setEnableLoadMore(true)
        mAdapter.removeAllHeaderView()
        mAdapter.removeAllFooterView()

        //添加头部
        if (header != null && header.isNotEmpty()){
            for (view in header) {
                mAdapter.addHeaderView(view)
            }
        }

        //添加尾部
        if (footer != null && footer.isNotEmpty()){
            for (view in footer) {
                mAdapter.addFooterView(view)
            }
        }

        //添加内容
        if (listData.isNotEmpty()) {
            mAdapter.setNewData(listData)
        } else {
            mLayoutStatusView?.showEmpty()
        }

        stopRefresh()
    }


    override fun loadMoreFail() {
        VLogUtil.e("loadMoreFail")
        //加载更多数据失败
        VLogUtil.e("加载更多数据失败")
        mSwipeRefreshLayout.isEnabled = true //加载更多时不能同时下拉刷新
        mAdapter.loadMoreFail()

        stopRefresh()
    }

    override fun setMoreData(listData: List<T>) {
        VLogUtil.e("setMoreData")
        //加载下一页数据
        VLogUtil.e("加载下一页数据")
        mSwipeRefreshLayout.isEnabled = true //加载更多时不能同时下拉刷新
        if (listData.isNotEmpty()) {
            VLogUtil.e("mAdapter.addItemData")
            mAdapter.addData(listData)
            mAdapter.loadMoreComplete()//加载更多完成
        }else{
            showToast("没有更多了")
            mAdapter.loadMoreEnd()//没有更多了
        }

        stopRefresh()
    }


    /**取消刷新动画*/
    private fun stopRefresh() {
        if (mSwipeRefreshLayout.isRefreshing!!) {
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.refresh_fab_top-> {
                mRefreshRecyclerView.scrollToPosition(0)
                mFloatingActionButton.visibility = View.INVISIBLE
            }
        }
    }

    // =================================================================================

    /**一些特殊处理*/
    abstract fun initRefreshFragment()
    /**创建adapter*/
    abstract fun createCommonRecyAdapter(): BaseQuickAdapter<T, BaseViewHolder>
    /**adapter 的点击事件处理*/
    abstract fun addItemClickListener(mAdapter: BaseQuickAdapter<T, BaseViewHolder>)
    /**请求Api数据*/
    abstract fun onTargerRequestApi(isRefresh: Boolean, page: Int, limit: Int)

    /**
     * 是否显示通用toolBar 默认不显示
     *
     * @return false
     */
    open fun hasLoadMore(): Boolean {
        VLogUtil.e("hasLoadMore")
        return true
    }
    /**
     * 创建RecyclerView 的 LayoutManager， 默认LinearLayoutManager
     * @return  LinearLayoutManager
     */
    open fun createLayoutManager(): RecyclerView.LayoutManager {
        VLogUtil.e("createLayoutManager")
        return LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }
}