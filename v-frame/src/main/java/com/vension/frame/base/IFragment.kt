package com.vension.frame.base

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 14:50
 * 描  述：
 * ========================================================
 */

interface IFragment {

     fun startProxyActivity(_Class: Class<*>)
     fun startProxyActivity(_Class: Class<*>, bundle: Bundle)

     fun startProxyActivity(_Class: Class<*>, _ActivityOptionsCompat: ActivityOptionsCompat)
     fun startProxyActivity(_Class: Class<*>, bundle: Bundle, _ActivityOptionsCompat: ActivityOptionsCompat)

     fun startProxyActivityForResult(_Class: Class<*>, requestCode: Int)
     fun startProxyActivityForResult(_Class: Class<*>, bundle: Bundle, requestCode: Int)
     fun startProxyActivityForResult(_Class: Class<*>, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat)
     fun startProxyActivityForResult(_Class: Class<*>, bundle: Bundle, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat)

     /**
      * 是否显示通用toolBar
      *
      * @return true 默认显示
      */
      fun showToolBar(): Boolean = true


     /**
      * toolbar是否覆盖在内容区上方
      *
      * @return false 不覆盖  true 覆盖
      */
      fun isToolbarCover(): Boolean = false

     /**
      * 请求数据前是否检查网络连接
      *
      * @return false 默认不检查
      */
      fun isCheckNet(): Boolean = false

}