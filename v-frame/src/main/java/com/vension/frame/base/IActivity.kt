package com.vension.frame.base

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 14:47
 * 描  述：BaseActivity 的接口
 * ========================================================
 */

interface IActivity {

     fun startProxyActivity(_Class: Class<out Fragment>)
     fun startProxyActivity(_Class: Class<out Fragment>, bundle: Bundle)
     fun startProxyActivity(_Class: Class<*>, _ActivityOptionsCompat: ActivityOptionsCompat)
     fun startProxyActivity(_Class: Class<*>, bundle: Bundle, _ActivityOptionsCompat: ActivityOptionsCompat)

     fun startProxyActivityForResult(_Class: Class<out Fragment>, requestCode: Int)
     fun startProxyActivityForResult(_Class: Class<out Fragment>, bundle: Bundle, requestCode: Int)
     fun startProxyActivityForResult(_Class: Class<out Fragment>, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat)
     fun startProxyActivityForResult(_Class: Class<out Fragment>, bundle: Bundle, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat)

     /**
      * 是否显示通用toolBar 默认不显示
      *
      * @return false
      */
      fun showToolBar(): Boolean = false


     /**
      * toolbar是否覆盖在内容区上方
      *
      * @return false 不覆盖  true 覆盖
      */
      fun isToolbarCover(): Boolean = false

     /**
      * 是否开启侧滑返回
      *
      * @return true 默认开启
      */
      fun isEnableSlideClose(): Boolean = true

     /**
      *  获取bundle 中的数据
      */
     fun getBundleExtras(extras: Bundle)= extras

}