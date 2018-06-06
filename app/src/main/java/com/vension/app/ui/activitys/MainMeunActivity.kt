package com.vension.app.ui.activitys

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.vension.app.R
import com.vension.app.showToast
import com.vension.app.ui.fragments.TabFragment_1
import com.vension.app.ui.fragments.TabFragment_2
import com.vension.app.ui.fragments.TabFragment_3
import com.vension.app.ui.fragments.TabFragment_4
import com.vension.frame.base.VBaseActivity
import com.vension.frame.utils.navbar.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_main_meun.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/23 11:51
 * 描  述：主界面
 * ========================================================
 */

class MainMeunActivity : VBaseActivity() {

    private var _TabFragment_1: TabFragment_1? = null
    private var _TabFragment_2: TabFragment_2? = null
    private var _TabFragment_3: TabFragment_3? = null
    private var _TabFragment_4: TabFragment_4? = null

    //默认为0
    private var mIndex = 0

    override fun isEnableSlideClose(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex",0)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //记录fragment的位置,防止崩溃 activity被系统回收时，fragment错乱
        outState.putInt("currTabIndex", mIndex)
    }


    override fun attachLayoutRes(): Int = R.layout.activity_main_meun

    override fun initViewAndData(savedInstanceState: Bundle?) {
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(main_navigation)
        main_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        switchFragment(mIndex)
        main_navigation.menu.getItem(mIndex).isChecked = true
    }

    override fun requestLoadData() {
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(0)
            }
            R.id.navigation_discover -> {
                switchFragment(1)
            }
            R.id.navigation_hot -> {
                switchFragment(2)
            }
            R.id.navigation_me -> {
                switchFragment(3)
            }
        }
        true
    }


    /**
     * 切换Fragment
     * @param position 下标
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction!!)
        when (position) {
            0 -> _TabFragment_1?.let {
                transaction.show(it)
            } ?: TabFragment_1().let {
                _TabFragment_1 = it
                transaction.add(R.id.main_content, it, "home")
            }
            1 -> _TabFragment_2?.let {
                transaction.show(it)
            } ?: TabFragment_2().let {
                _TabFragment_2 = it
                transaction.add(R.id.main_content, it, "discovery") }
            2 -> _TabFragment_3?.let {
                transaction.show(it)
            } ?: TabFragment_3().let {
                _TabFragment_3 = it
                transaction.add(R.id.main_content, it, "hot") }
        //头条号
            3 -> _TabFragment_4?.let {
                transaction.show(it)
            } ?: TabFragment_4().let {
                _TabFragment_4 = it
                transaction.add(R.id.main_content, it, "mine") }
            else -> {
                transaction.add(R.id.main_content, Fragment(), "agent")
            }
        }
        transaction.commitAllowingStateLoss()
    }


    /**
     * 隐藏所有的Fragment
     * @param transaction transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        _TabFragment_1?.let { transaction.hide(it) }
        _TabFragment_2?.let { transaction.hide(it) }
        _TabFragment_3?.let { transaction.hide(it) }
        _TabFragment_4?.let { transaction.hide(it) }
    }

    private var exitTime: Long = 0
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - exitTime < 2000) {
            super.onBackPressed()
        } else {
            showToast("再按一次退出程序")
            exitTime = currentTime
        }
    }


}