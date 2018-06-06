package com.vension.frame.base

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.UiThread
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.FrameLayout
import butterknife.ButterKnife
import butterknife.Unbinder
import com.tbruyelle.rxpermissions2.RxPermissions
import com.vension.frame.R
import com.vension.frame.VFrame
import com.vension.frame.dialogs.VLoadingDialog
import com.vension.frame.utils.VLogUtil
import com.vension.frame.views.MultipleStatusView
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import org.greenrobot.eventbus.EventBus

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/18 10:35
 * 描  述：无 MVP 的 Fragment-基类
 * ========================================================
 */

abstract class VBaseFragment : Fragment(),IFragment,View.OnClickListener {

    protected lateinit var TAG_LOG: String
    protected var rootView: View? = null //在使用自定义toolbar时候的根布局 = toolBarView+childView
    protected var mLayoutStatusView: MultipleStatusView? = null //多种状态的 View 的切换
    protected lateinit var mCommonTitleBar: CommonTitleBar //多种状态的 View 的切换
    protected lateinit var mRxPermissions: RxPermissions //6.0动态获取权限
    private var isViewPrepare = false //视图是否加载完毕
    private var hasLoadData = false   //是否加载过数据
    private lateinit var mUnBinder: Unbinder

    protected val mloadingDialog by lazy {
        VLoadingDialog.with(context)
                .setCanceled(false)
                .setOrientation(VLoadingDialog.VERTICAL)
                .setBackgroundColor(Color.parseColor("#80000000"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG_LOG = this.javaClass.simpleName
        getBundleExtras(arguments)
        mRxPermissions = RxPermissions(context as Activity)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null){
            //为空时初始化。
            if (showToolBar() && getToolBarResId() != -1) {
                //如果需要显示自定义toolbar,并且资源id存在的情况下，实例化baseView;
                rootView = inflater.inflate(if (isToolbarCover())
                    R.layout.activity_base_toolbar_cover
                else
                    R.layout.activity_base, container, false)//根布局
                val vsToolbar = rootView?.findViewById<View>(R.id.vs_toolbar) as ViewStub//toolbar容器
                val flContainer = rootView?.findViewById<View>(R.id.fl_container) as FrameLayout//子布局容器
                vsToolbar.layoutResource = getToolBarResId()//toolbar资源id
                vsToolbar.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                vsToolbar.inflate()//填充toolbar
                inflater.inflate(attachLayoutRes(), flContainer, true)//子布局
            } else {
                //不显示通用toolbar
                rootView = inflater.inflate(attachLayoutRes(), container, false)
            }
        }
        VLogUtil.e("V_BaseFragment ==> onCreateView")
        return rootView
    }

    /**
     * 第一步,改变isPrepared标记
     * 当onViewCreated()方法执行时,表明View已经加载完毕,此时改变isPrepared标记为true,并调用lazyLoad()方法
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        VLogUtil.e("V_BaseFragment ==> onViewCreated")
        isViewPrepare = true
        if (showToolBar()){
            mCommonTitleBar = rootView?.findViewById<View>(R.id.mCommonTitleBar) as CommonTitleBar//子布局容器
            initToolBar(mCommonTitleBar!!)//初始化标题栏
        }
        //绑定view
        rootView?.let { ButterKnife.bind(this,it) }
        initViewAndData(savedInstanceState)
        lazyLoadDataIfPrepared()
        //多种状态切换的view 重试点击事件
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //如果fragment可见进行懒加载数据
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (rootView == null) {
            //如果view还未初始化，不进行处理
            return
        }
        VLogUtil.e("V_BaseFragment ==> setUserVisibleHint= $isVisibleToUser")
        if (isVisibleToUser) {
            VLogUtil.e("V_BaseFragment ==> lazyLoadDataIfPrepared")
            lazyLoadDataIfPrepared()//请求网络数据
        }
    }


    /**初始化标题栏*/
    open fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setListener {_: View?, action: Int, extra: String? ->
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON || action == CommonTitleBar.ACTION_LEFT_TEXT) {
                activity?.onBackPressed()
            }
        }
    }

    /**
     * 页面初始化完进行懒加载
     * 第三步:lazyLoadDataIfPrepared()方法中进行双重标记判断,通过后即可进行数据加载
     */
    private fun lazyLoadDataIfPrepared() {
        var canLoad : Boolean = userVisibleHint && isViewPrepare && !hasLoadData
        VLogUtil.e("V_BaseFragment ==> lazyLoadDataIfPrepared= $canLoad")
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            VLogUtil.e("V_BaseFragment ==> lazyLoadData")
            lazyLoadData()
            hasLoadData = true
        }
    }


    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoadData()
    }

    override fun onClick(view: View?) {

    }
    /** ======================= 抽象方法 ============================= */

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
     * 第四步:定义抽象方法lazyLoadData(),具体加载数据的工作,交给子类去完成
     */
    @UiThread
    abstract fun lazyLoadData()

    /** =========================== ============================ */



    /**
     * 获取自定义toolbarview 资源id 默认为-1，
     * showToolBar()方法必须返回true才有效
     */
    private fun getToolBarResId(): Int {
        return getToolBarResId(0)
    }

    open fun getToolBarResId(layout : Int): Int {
        return if (layout > 0) layout else R.layout.v_layout_default_toolbar
    }

    /**
     * 获取bundle中相应data
     */
    open fun getBundleExtras(extras: Bundle? = Bundle()) {}


    /**重置变量 */
    private fun resetVariavle() {
        isViewPrepare = false
        hasLoadData = false
    }


    override fun onDestroy() {
        mloadingDialog.dismiss()
        super.onDestroy()
        VBaseApplication.getRefWatcher(activity!!).watch(activity)
    }

    /** =========================== ============================ */

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


    /**
     * 跳转到相应的activity 并携带bundle数据
     */
    protected fun gotoActivity(clazz: Class<out Any>, bundle: Bundle? = null) {
        val intent = Intent(activity, clazz)
        bundle?.let {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * 跳转到相应的activity,并携带bundle数据，接收返回码
     */
    protected fun gotoActivityForResult(clazz: Class<out Any>, bundle: Bundle? = null, requestCode: Int) {
        val intent = Intent(activity, clazz)
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
        val intent = Intent(activity, clazz)
        bundle?.let {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        activity?.finish()
    }


    override fun startProxyActivity(_Class: Class<*>) {
        startActivity(VFrame.createProxyIntent(activity, _Class))
    }

    override fun startProxyActivity(_Class: Class<*>, bundle: Bundle) {
        startActivity(VFrame.createProxyIntent(activity, _Class, bundle))
    }

    override fun startProxyActivity(_Class: Class<*>, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivity(activity!!, VFrame.createProxyIntent(activity, _Class), _ActivityOptionsCompat.toBundle())
    }

    override fun startProxyActivity(_Class: Class<*>, bundle: Bundle, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivity(activity!!, VFrame.createProxyIntent(activity, _Class, bundle), _ActivityOptionsCompat.toBundle())
    }

    override fun startProxyActivityForResult(_Class: Class<*>, requestCode: Int) {
        startActivityForResult(VFrame.createProxyIntent(activity, _Class), requestCode)
    }

    override fun startProxyActivityForResult(_Class: Class<*>, bundle: Bundle, requestCode: Int) {
        startActivityForResult(VFrame.createProxyIntent(activity, _Class, bundle), requestCode)
    }

    override fun startProxyActivityForResult(_Class: Class<*>, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivityForResult(activity!!, VFrame.createProxyIntent(activity, _Class), requestCode, _ActivityOptionsCompat.toBundle())
    }

    override fun startProxyActivityForResult(_Class: Class<*>, bundle: Bundle, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivityForResult(activity!!, VFrame.createProxyIntent(activity, _Class, bundle), requestCode, _ActivityOptionsCompat.toBundle())
    }


}