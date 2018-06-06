package com.vension.frame.base

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import butterknife.ButterKnife
import butterknife.Unbinder
import com.gw.swipeback.WxSwipeBackLayout
import com.tbruyelle.rxpermissions2.RxPermissions
import com.vension.frame.R
import com.vension.frame.VFrame
import com.vension.frame.dialogs.VLoadingDialog
import com.vension.frame.views.MultipleStatusView
import com.wuhenzhizao.titlebar.utils.AppUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import org.greenrobot.eventbus.EventBus

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/17 14:14
 * 描  述：无MVP的activity-基类
 * ========================================================
 */

abstract class VBaseActivity : AppCompatActivity(),IActivity {

    protected lateinit var TAG_LOG: String
    protected lateinit var mContext: Context //上下文对象
    protected var rootView: View? = null //在使用自定义toolbar时候的根布局 = toolBarView+childView
    protected var mLayoutStatusView: MultipleStatusView? = null //多种状态的 View 的切换
    protected lateinit var mRxPermissions: RxPermissions //6.0动态获取权限
    private lateinit var mUnBinder: Unbinder

    protected val mloadingDialog by lazy {
        VLoadingDialog.with(this)
                .setCanceled(false)
                .setOrientation(VLoadingDialog.VERTICAL)
                .setBackgroundColor(Color.parseColor("#80000000"))
    }

    /**
     * 跳转到其他Activity启动或者退出的模式
     */
    enum class TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        KeyboardConflictCompat.assistWindow(window)//
        AppUtils.transparencyBar(window)//透明状态栏
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(attachLayoutRes())//加载布局
            TAG_LOG = this.javaClass.simpleName //获取上下文并设置log标记
            mContext = this
            mRxPermissions = RxPermissions(this)
            mUnBinder = ButterKnife.bind(this)

            //设置ToolBar
            if (showToolBar()){
                var mCommonTitleBar = rootView?.findViewById<View>(R.id.mCommonTitleBar) as CommonTitleBar//子布局容器
                initToolBar(mCommonTitleBar)
            }

            //是否开启侧滑返回
            if (isEnableSlideClose()){
                val mSwipeBackLayout = WxSwipeBackLayout(this)
                mSwipeBackLayout.maskAlpha = 180
                mSwipeBackLayout.attachToActivity(this)
            }

            val extras = intent.extras
            if (extras != null) {
                getBundleExtras(extras)
            }else{
                getBundleExtras(Bundle())
            }

            overrideTransitionAnimation()
            initViewAndData(savedInstanceState)//初始化view和数据
            requestLoadData()//请求网络数据
            setRetryClickListener()//设置重新加载按钮点击监听

        }catch (ex : Exception){
            ex.printStackTrace()
        }
    }


    override fun setContentView(@LayoutRes layoutResID: Int) {
        if (layoutResID == 0){
            throw RuntimeException("layoutResID == -1 have u create your layout?")
        }
        if (showToolBar() && getToolBarResId() != -1){
            //如果需要显示自定义toolbar,并且资源id存在的情况下，实例化baseView
            rootView = LayoutInflater.from(this).inflate(if (isToolbarCover()) R.layout.activity_base_toolbar_cover
            else R.layout.activity_base,null,false)//根布局

            //设置layout
            val vsToolbar = rootView?.findViewById<View>(R.id.vs_toolbar) as ViewStub//toolbar容器
            val flContainer = rootView?.findViewById<View>(R.id.fl_container) as FrameLayout//子布局容器
            vsToolbar.layoutResource = getToolBarResId()//toolbar资源id
            vsToolbar.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            vsToolbar.inflate()//填充toolbar
            LayoutInflater.from(this).inflate(layoutResID, flContainer, true)//子布局
            setContentView(rootView)
        }else{
            //不显示通用toolbar
            super.setContentView(layoutResID)
        }
    }

    /**设置重新加载按钮点击监听*/
    private fun setRetryClickListener() {
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        requestLoadData()
    }

    /**初始化标题栏*/
    private fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setListener {_: View?, action: Int, extra: String? ->
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON || action == CommonTitleBar.ACTION_LEFT_TEXT) {
                onBackPressed()
            }
        }
    }

    /**
     * 设置activity进入动画
     */
    private fun overrideTransitionAnimation() {
        if (toggleOverridePendingTransition()) {
            when (getOverridePendingTransition()) {
                TransitionMode.TOP -> overridePendingTransition(R.anim.top_in, R.anim.no_anim)
                TransitionMode.BOTTOM -> overridePendingTransition(R.anim.bottom_in, R.anim.no_anim)
                TransitionMode.LEFT -> overridePendingTransition(R.anim.left_in, R.anim.no_anim)
                TransitionMode.RIGHT -> overridePendingTransition(R.anim.right_in, R.anim.no_anim)
                TransitionMode.FADE -> overridePendingTransition(R.anim.fade_in, R.anim.no_anim)
                TransitionMode.SCALE -> overridePendingTransition(R.anim.scale_in, R.anim.no_anim)
            }
        }
    }


    override fun finish() {
        super.finish()
        if (toggleOverridePendingTransition()) {
            when (getOverridePendingTransition()) {
                TransitionMode.TOP -> overridePendingTransition(0, R.anim.top_out)
                TransitionMode.BOTTOM -> overridePendingTransition(0, R.anim.bottom_out)
                TransitionMode.LEFT -> overridePendingTransition(0, R.anim.left_out)
                TransitionMode.RIGHT -> overridePendingTransition(0, R.anim.right_out)
                TransitionMode.FADE -> overridePendingTransition(0, R.anim.fade_out)
                TransitionMode.SCALE -> overridePendingTransition(0, R.anim.scale_out)
            }
        }
    }

    override fun onDestroy() {
        mUnBinder.unbind()
        rootView = null
        mloadingDialog.dismiss()
        super.onDestroy()
        VBaseApplication.getRefWatcher(this).watch(this)
    }

    override fun onBackPressed() {
        // Fragment 逐个出栈
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    /** ============================= 抽象方法 ===============================*/
    /**
     *  加载布局
     */
    @LayoutRes
    abstract fun attachLayoutRes(): Int

    /**
     *  请求数据前的一些初始化设置
     */
    abstract fun initViewAndData(savedInstanceState: Bundle?)

    /**
     * 请求加载网络数据
     */
    abstract fun requestLoadData()

    //=============================================================


    /**
     * 获取自定义toolbarview 资源id 默认为-1，
     * showToolBar()方法必须返回true才有效
     */
    private fun getToolBarResId(): Int {
        return R.layout.v_layout_default_toolbar
    }


    /**
     * 是否有切换动画
     */
    protected open fun toggleOverridePendingTransition() = false

    /**
     * 获得切换动画的模式
     */
    protected open fun getOverridePendingTransition(): TransitionMode? = null




    //=================== =============================================

    /**
     * 跳转到相应的activity 并携带bundle数据
     */
    protected fun gotoActivity(clazz: Class<out Any>, bundle: Bundle? = null) {
        val intent = Intent(this, clazz)
        bundle?.let {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * 跳转到相应的activity,并携带bundle数据，接收返回码
     */
    protected fun gotoActivityForResult(clazz: Class<out Any>, bundle: Bundle? = null, requestCode: Int) {
        val intent = Intent(this, clazz)
        bundle?.let {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    /**
     * 跳转到相应的activity并携带bundle数据，然后干掉自己
     *
     */
    protected fun gotoActivityThenKillSelf(clazz: Class<out Any>, bundle: Bundle? = null) {
        val intent = Intent(this, clazz)
        bundle?.let {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        finish()
    }

    override fun startProxyActivity(_Class: Class<out Fragment>) {
        startActivity(VFrame.createProxyIntent(this, _Class))
    }

    override fun startProxyActivity(_Class: Class<out Fragment>, bundle: Bundle) {
        startActivity(VFrame.createProxyIntent(this, _Class, bundle))
    }

    override fun startProxyActivity(_Class: Class<*>, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivity(this, VFrame.createProxyIntent(this, _Class), _ActivityOptionsCompat.toBundle())
    }

    /**
     *  val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, iv_search, iv_search.transitionName)
     *  startActivity(Intent(activity, SearchActivity::class.java), options.toBundle())
     **/
    override fun startProxyActivity(_Class: Class<*>, bundle: Bundle, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivity(this, VFrame.createProxyIntent(this, _Class, bundle), _ActivityOptionsCompat.toBundle())
    }

    override fun startProxyActivityForResult(_Class: Class<out Fragment>, requestCode: Int) {
        startActivityForResult(VFrame.createProxyIntent(this, _Class), requestCode)
    }

    override fun startProxyActivityForResult(_Class: Class<out Fragment>, bundle: Bundle, requestCode: Int) {
        startActivityForResult(VFrame.createProxyIntent(this, _Class, bundle), requestCode)
    }

    override fun startProxyActivityForResult(_Class: Class<out Fragment>, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivityForResult(this, VFrame.createProxyIntent(this, _Class), requestCode, _ActivityOptionsCompat.toBundle())
    }

    override fun startProxyActivityForResult(_Class: Class<out Fragment>, bundle: Bundle, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivityForResult(this, VFrame.createProxyIntent(this, _Class, bundle), requestCode, _ActivityOptionsCompat.toBundle())
    }

    /** =============================== ===================================== */
    /**
     * 打卡软键盘
     */
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    fun isEventBusRegisted(subscribe: Any): Boolean {
        return EventBus.getDefault().isRegistered(subscribe)
    }

    fun registerEventBus(subscribe: Any) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe)
        }
    }

    fun unregisterEventBus(subscribe: Any) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe)
        }
    }


// ====================================添加fragment=======================================
    /**
     * 添加 Fragment
     * @param containerViewId
     * @param fragment
     */
    protected fun addFragment(containerViewId: Int, fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(containerViewId, fragment)
        fragmentTransaction.commit()
    }


    /**
     * 添加 Fragment
     * @param containerViewId
     * @param fragment
     */
    protected fun addFragment(containerViewId: Int, fragment: Fragment, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        // 设置tag，不然下面 findFragmentByTag(tag)找不到
        fragmentTransaction.add(containerViewId, fragment, tag)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }

    /**
     * 替换 Fragment
     * @param containerViewId
     * @param fragment
     */
    protected fun replaceFragment(containerViewId: Int, fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(containerViewId, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    /**
     * 替换 Fragment
     * @param containerViewId
     * @param fragment
     */
    protected fun replaceFragment(containerViewId: Int, fragment: Fragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            // 设置tag
            fragmentTransaction.replace(containerViewId, fragment, tag)
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            // 这里要设置tag，上面也要设置tag
            fragmentTransaction.addToBackStack(tag)
            fragmentTransaction.commit()
        } else {
            // 存在则弹出在它上面的所有fragment，并显示对应fragment
            supportFragmentManager.popBackStack(tag, 0)
        }
    }



}