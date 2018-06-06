package com.vension.app

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.vension.app.utils.glide.GlideImageLoader
import com.vension.frame.VFrame
import com.vension.frame.VFrame.initHttpConfig
import com.vension.frame.base.VBaseApplication
import com.vension.frame.utils.AppFrontBackHelper

/**
 * @author ：Created by Administrator on 2018/5/18 10:15.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class MyApplication : VBaseApplication() {

    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator{context, layout ->
            //全局设置主题颜色
            layout.setPrimaryColorsId(R.color.app_main_backgroup_color, android.R.color.white)
            //指定为经典Header，默认是 贝塞尔雷达Header
            MaterialHeader(context)
//            PhoenixHeader(context)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20F)
        }
    }

    companion object {

        private lateinit var mApplication: Application
        /**
         * 获取当前应用上下文对象
         */
        fun getAppContext(): Context {
            return mApplication
        }

        /**
         * 获取资源文件访问对象
         */
        fun getResource(): Resources {
            return mApplication.resources
        }

    }

    override fun onCreate() {
        super.onCreate()
        //todo 这里还要做崩溃检查 腾讯的bugly 热更新等操作
        //初始化多状态界面View
//        VFrame.initXLoadingView().setErrorViewResId(R.layout._loading_layout_error);
        /**
        初始化网络请求的引擎,在这里可以一行代码切换，避免更换网络框架麻烦的问题
        提供三种常见框架的简单案例：（你也可以按照例子自己实现）
        AsyncHttpEngine、OKHttpEngine、VolleyHttpEngine
         */
        initHttpConfig(Constant.JUHE_BASE_URL)
        /**
         * 初始化全局图片加载框架
         * GlideImageLoader为你的图片加载框架实现类
         */
        VFrame.initImageLoader(GlideImageLoader(applicationContext))
        //初始化数据库
//        Realm.init(applicationContext)
        AppFrontBackHelper().register(this, object : AppFrontBackHelper.OnAppStatusListener {
            override fun onFront() {
                //TODO 应用切到前台处理

            }

            override fun onBack() {
                //TODO 应用切到后台处理
            }
        })
    }

}