package com.vension.app.ui.activitys

import android.Manifest
import android.graphics.Typeface
import android.os.Bundle
import android.view.KeyEvent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.jennifer.andy.simpleeyes.utils.AppUtil
import com.vension.app.R
import com.vension.app.showToast
import com.vension.app.startMainActivity
import com.vension.app.ui.fragments.GuideFragment
import com.vension.frame.VFrame
import com.vension.frame.base.VBaseActivity
import com.vension.frame.utils.SharedPreferenceUtil
import com.vension.frame.utils.TimeUtil
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/22 15:31
 * 描  述：启动页
 * connect haima --> adb connect 127.0.0.1:26944
 * ========================================================
 */

class SplashActivity : VBaseActivity() {


    private var textTypeface : Typeface?= null
    private var descTypeFace : Typeface?= null
    private var alphaAnimation : AlphaAnimation?= null

    init {
        textTypeface = Typeface.createFromAsset(VFrame.getContext().assets, "fonts/Lobster-1.4.otf")
        descTypeFace = Typeface.createFromAsset(VFrame.getContext().assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun isEnableSlideClose(): Boolean {
        return false
    }


    override fun attachLayoutRes(): Int = R.layout.activity_splash

    override fun initViewAndData(savedInstanceState: Bundle?) {
        tv_app_name.typeface = textTypeface
        tv_version.typeface = descTypeFace
        val welcome_hint = getString(R.string.welcome_hint_noVersion,"2014", TimeUtil.getNowString("yyyy"), "kevin~vension.com")
        tv_version.text = welcome_hint

        //渐变展示启动屏
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 3000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                redirectToMain()
                checkPermission()
            }

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationStart(animation: Animation) {}

        })
        layout_splash.startAnimation(alphaAnimation)

    }

    override fun requestLoadData() {
    }


    /**
     * 6.0以下版本(系统自动申请) 不会弹框
     * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
     */
    private fun checkPermission(){
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                .subscribe { granted ->
                    if (granted) {
                        // All requested permissions are granted
                    } else {
                        // At least one permission is denied
                        showToast("用户关闭了权限")
                    }
                }
    }

    /**进入主界面*/
    fun redirectToMain() {
        val code = SharedPreferenceUtil.getSingleInstance(VFrame.getContext()).getInt("version_code")
        //是否第一次进入App
        if (code < AppUtil.getAppVersionCode(this)) {
            //进入向导界面
            startProxyActivity(GuideFragment::class.java)
        } else {
            //进入主界面
           startMainActivity(this)
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        alphaAnimation?.cancel()
    }

    //设置点击返回键不响应
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }


}
