package com.vension.app.ui.fragments

import android.os.Bundle
import android.widget.ImageView
import butterknife.OnClick
import cn.bingoogolapple.bgabanner.BGALocalImageSize
import com.jennifer.andy.simpleeyes.utils.AppUtil
import com.vension.app.R
import com.vension.app.startMainActivity
import com.vension.frame.VFrame
import com.vension.frame.base.VBaseFragment
import com.vension.frame.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.fragment_guide.*



/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/22 17:03
 * 描  述：引导页
 * ========================================================
 */

class GuideFragment : VBaseFragment(){

    override fun attachLayoutRes(): Int = R.layout.fragment_guide

    override fun showToolBar(): Boolean {
        return false
    }

    override fun onResume() {
        super.onResume()
        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        banner_guide.setBackgroundResource(android.R.color.white)
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        setBanners()// 设置数据源
    }


    override fun lazyLoadData() {
    }

    /**
     * 设置banner 引导数据
     */
    private fun setBanners() {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        val localImageSize = BGALocalImageSize(720, 1280, 320f, 640f)
        // 设置数据源
        banner_guide.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.mipmap.img_welcome_1, R.mipmap.img_welcome_2, R.mipmap.img_welcome_3, R.mipmap.img_welcome_4)
    }

    @OnClick(R.id.btn_guide_enter)
    fun onClick() {
        SharedPreferenceUtil.getSingleInstance(VFrame.getContext()).put("version_code", AppUtil.getAppVersionCode(VFrame.getContext())).commit()
        startMainActivity(activity!!)
        activity?.finish()
    }

}