package com.vension.app.ui.fragments

import android.graphics.Color
import android.os.Bundle
import com.vension.app.R
import com.vension.app.widgets.MultiElementProgress
import com.vension.frame.base.VBaseFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/28 15:50
 * 描  述：
 * ========================================================
 */

class LoginFragment : VBaseFragment(){
    override fun attachLayoutRes(): Int = R.layout.fragment_login

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        mCommonTitleBar.centerTextView.text = "登录"
    }
    override fun initViewAndData(savedInstanceState: Bundle?) {
        multi_progress.setProgress(getLists())
    }

    private fun getLists(): List<MultiElementProgress.Element> {
        val datas = ArrayList<MultiElementProgress.Element>()
        val  e1 = MultiElementProgress.Element(Color.parseColor("#2cc6ba"),20f)
        val  e2 = MultiElementProgress.Element(Color.parseColor("#5BB75B"),30f)
        val  e3 = MultiElementProgress.Element(Color.parseColor("#fff55c"),10f)
        val  e4 = MultiElementProgress.Element(Color.parseColor("#eadc4d"),40f)
        datas.add(e1)
        datas.add(e2)
        datas.add(e3)
        datas.add(e4)
        return datas
    }

    override fun lazyLoadData() {
    }
}