package com.vension.app.ui.fragments.agent

import android.os.Bundle
import com.vension.app.R
import com.vension.frame.base.VBaseFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_test.*

/**
 * @author ：Created by Administrator on 2018/4/4 10:54.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class TestFragment : VBaseFragment() {

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): TestFragment {
            val fragment = TestFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }


    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        mCommonTitleBar.centerTextView.text = mTitle
    }


    override fun attachLayoutRes(): Int {
        return R.layout.fragment_test
    }


    override fun initViewAndData(savedInstanceState: Bundle?) {
        tv_content.text = mTitle
    }

    override fun lazyLoadData() {
    }

}