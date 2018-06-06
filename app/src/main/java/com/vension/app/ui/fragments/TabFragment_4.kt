package com.vension.app.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.NestedScrollView
import android.view.View
import com.vension.app.R
import com.vension.app.showToast
import com.vension.app.ui.fragments.agent.TestListFragment
import com.vension.frame.base.VBaseFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_tab_4.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/23 14:29
 * 描  述：我的
 * ========================================================
 */

class TabFragment_4 : VBaseFragment() {


    override fun isToolbarCover(): Boolean {
        return true
    }

    override fun getToolBarResId(layout: Int): Int {
        return R.layout.layout_toolbar_mine
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.alpha = 0.0f
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_tab_4

    override fun initViewAndData(savedInstanceState: Bundle?) {
        myNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val height = layout_top.height
            var alpha = 1.0f
            if (mCommonTitleBar?.top!! + scrollY < height) {
                alpha = scrollY / (height - mCommonTitleBar?.height!!).toFloat()
            }
            mCommonTitleBar?.alpha = alpha
        })

        clipCorner.setOnClickListener(this)
        view_my_money_package.setOnClickListener(this)
        view_my_order.setOnClickListener(this)
        view_shopping_cart.setOnClickListener(this)
        view_my_collection.setOnClickListener(this)
        view_my_focus.setOnClickListener(this)
        view_my_message.setOnClickListener(this)
    }

    override fun lazyLoadData() {
    }


    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.clipCorner-> {
                startProxyActivity(TestListFragment::class.java)
            }
            R.id.view_my_money_package-> {
                showToast("我的钱包")
                mloadingDialog.show()
                Handler().postDelayed({
                    mloadingDialog.dismiss()
                }, 2000)
            }
            R.id.view_my_order-> {
                showToast("我的订单")
                startProxyActivity(LoginFragment::class.java)
            }
            R.id.view_shopping_cart-> showToast("我的购物车")
            R.id.view_my_collection-> showToast("我的收藏")
            R.id.view_my_focus-> showToast("我的关注")
            R.id.view_my_message-> showToast("我的消息")
        }
    }

}