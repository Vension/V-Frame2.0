package com.vension.app.ui.fragments

import android.os.Bundle
import android.view.View
import com.vension.app.R
import com.vension.frame.base.VBaseFragment
import com.vension.frame.imageloader.VImage
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_tab_3.*

/**
 * @author ：Created by Administrator on 2018/5/23 15:52.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class TabFragment_3 : VBaseFragment() {

    val banner_url = "http://ww1.sinaimg.cn/large/0065oQSqly1frjd4var2bj30k80q0dlf.jpg"

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.centerTextView.text = "Glide加载网络图片"
        mCommonTitleBar.leftImageButton.visibility = View.GONE
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_tab_3

    override fun initViewAndData(savedInstanceState: Bundle?) {
        VImage.getInstance().load(iv_banner,banner_url)
//        GlideImageLoader.create(iv_banner).loadImage(banner_url,R.color.placeholder_color)
    }

    override fun lazyLoadData() {
    }

}