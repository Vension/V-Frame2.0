package com.vension.frame.base

import android.content.Context
import android.content.res.Configuration
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.gw.swipeback.tools.WxSwipeBackActivityManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.vension.frame.VFrame
import com.vension.frame.imageloader.ImageLoadHelper
import com.vension.frame.utils.FileCache
import kotlin.properties.Delegates

/**
 * @author ：Created by Administrator on 2018/5/9 14:13.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
open class VBaseApplication : MultiDexApplication(){

    private lateinit var mRefWatcher : RefWatcher

    companion object {
        private val TAG = "VFrame2.0"
        var mContext : Context by Delegates.notNull()

        fun getRefWatcher(context : Context) : RefWatcher {
            val mVApplication = context.applicationContext as VBaseApplication
            return mVApplication.mRefWatcher
        }

    }

    /**
     * 这个最先执行
     */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    /**
     * 程序启动的时候执行
     */
    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        mRefWatcher = setupLeakCanary()
        initConfig()//初始化配置
        //在子线程中完成其他初始化
//        InitializeService.start(this)
    }

    /**
     * 初始化配置
     */
    private fun initConfig() {
        VFrame.init(this)
        Logger.addLogAdapter(AndroidLogAdapter())//初始化logger
        //侧滑返回初始化
        WxSwipeBackActivityManager.getInstance().init(this)
        // 文件初始化
        FileCache.onInit(this)
        VFrame.initLog()
        VFrame.initToast()
        VFrame.initCrash()
        ImageLoadHelper.initImageLoader(applicationContext)
    }


    private fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }


    /**
     * 低内存的时候执行
     */
    override fun onLowMemory() {
        super.onLowMemory()
        Log.d("V_Application", "onLowMemory")
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    /**
     * HOME键退出应用程序
     * 程序在内存清理的时候执行
     */
    override fun onTrimMemory(level: Int) {
        Log.d("V_Application", "onTrimMemory")
        super.onTrimMemory(level)
    }

    /**
     * 程序终止的时候执行
     */
    override fun onTerminate() {
        Log.d("V_Application", "onTerminate")
        super.onTerminate()
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        Log.d("V_Application", "onConfigurationChanged")
        super.onConfigurationChanged(newConfig)
    }


}