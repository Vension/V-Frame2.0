package com.vension.frame.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.WindowManager
import com.vension.frame.R
import com.vension.frame.VFrame

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 16:00
 * 描  述：代理Activity
 * ========================================================
 */

class ProxyAvtivity : VBaseActivity() {

    private var mFragment: VBaseFragment? = null

    override fun attachLayoutRes(): Int {
        return R.layout.activity_proxy
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        try {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            // setup main fragment
            val fBundle = intent.extras
            val fFragmentClass = fBundle.getString(VFrame.PROXY_FRAGMENT_CLASS_KEY)
            if (!TextUtils.isEmpty(fFragmentClass)) {
                //状态栏是否可见
//                if (!fBundle.containsKey(Constant.AGENT_TOOLBAR_DIAPLAY)) {
//                    fBundle.putBoolean(Constant.AGENT_TOOLBAR_DIAPLAY, true)
//                }
//                //返回按钮是否显示
//                if (!fBundle.containsKey(Constant.AGENT_TOOLBAR_BACK_ENABLE)) {
//                    fBundle.putBoolean(Constant.AGENT_TOOLBAR_BACK_ENABLE, true)
//                }
                mFragment = Class.forName(fFragmentClass).newInstance() as VBaseFragment
                mFragment?.arguments = fBundle
                mFragment?.let { setMainFragment(it) }
            }
        } catch (e: Exception) {
            finish()
        }
    }

    override fun requestLoadData() {
    }


    private fun setMainFragment(fragment: Fragment) {
        setMainFragment(fragment, 0, 0)
    }


    private fun setMainFragment(fragment: Fragment, enterAnim: Int, exitAnim: Int) {
        if (!this.isFinishing) {
            val mFragmentTransaction = supportFragmentManager.beginTransaction()
            if (enterAnim != 0 && exitAnim != 0) {
                mFragmentTransaction.setCustomAnimations(enterAnim, exitAnim)
            }
            mFragmentTransaction.replace(R.id.proxy_content, fragment)
            mFragmentTransaction.commitAllowingStateLoss()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        if (mFragment != null) mFragment = null
    }

}