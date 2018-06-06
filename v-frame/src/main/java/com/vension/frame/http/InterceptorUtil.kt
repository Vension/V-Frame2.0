package com.vension.frame.http

import android.util.Log
import com.jennifer.andy.simpleeyes.utils.AppUtil
import com.vension.frame.VFrame
import com.vension.frame.utils.NetworkUtil
import com.vension.frame.utils.VPreference
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/24 12:08
 * 描  述：公用的网络拦截器
 * ========================================================
 */

object InterceptorUtil {


    /**日志拦截器 */
    fun initLogInterceptor(): Interceptor {
        //添加一个log拦截器,打印所有的log
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            message -> Log.i("info", "请求参数:$message")
        })
        //可以设置请求过滤的水平,body,basic,headers
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY//设置打印数据的级别
        return httpLoggingInterceptor
    }

    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    // Provide your custom parameter here
                    .addQueryParameter("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/8.0.0.13547")
                    .addQueryParameter("Cache-Control", "max-age=0")
                    .addQueryParameter("Upgrade-Insecure-Requests", "1")
                    .addQueryParameter("X-Requested-With", "XMLHttpRequest")
                    .addQueryParameter("Cookie", "uuid=\"w:f2e0e469165542f8a3960f67cb354026\"; __tasessionId=4p6q77g6q1479458262778; csrftoken=7de2dd812d513441f85cf8272f015ce5; tt_webid=36385357187")
                    .addQueryParameter("phoneSystem", "")
                    .addQueryParameter("phoneModel", "")
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置头部参数 HeaderInterceptor
     */
    private var token:String by  VPreference("token","")
     fun addHeaderParamInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    // Provide your custom header here
                    .header("token", token)
                    .header ("Content-Type", "application/json; charset=UTF-8")
                    .header ("Connection", "keep-alive")
                    .header ("Accept", "*/*")
                    .header ("Cache-Control", String.format("public, max-age=%d", 60))

//                    .header ("Authorization", _Authorization)
//                    .header ("X-Oc-TimeStamp", _TimeStamp)
                    .header ("X-Oc-Device-Model", android.os.Build.MODEL)
                    .header ("X-Oc-Os-Model", "Android " + android.os.Build.VERSION.RELEASE)
                    .header ("X-Oc-App-Bundle", AppUtil.getAppVersionCode(VFrame.getContext()).toString())
                    .header ("X-Oc-App-Version", AppUtil.getAppVersionName(VFrame.getContext()))
                    .header ("X-Oc-Merchant-Language", "2")
//                    .header ("X-Oc-Sign", MD5Helper.MD5(_SignedString).toString().toLowerCase())
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }


    /**
     * 缓存机制
     * 在响应请求之后在 data/data/<包名>/cache 下建立一个response 文件夹，保持缓存数据。
     * 这样我们就可以在请求的时候，如果判断到没有网络，自动读取缓存的数据。
     * 同样这也可以实现，在我们没有网络的情况下，重新打开App可以浏览的之前显示过的内容。
     * 也就是：判断网络，有网络，则从网络获取，并保存到缓存中，无网络，则从缓存中获取。
     * https://werb.github.io/2016/07/29/%E4%BD%BF%E7%94%A8Retrofit2+OkHttp3%E5%AE%9E%E7%8E%B0%E7%BC%93%E5%AD%98%E5%A4%84%E7%90%86/
     */
     fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            //网络不可用
            if (!NetworkUtil.isNetworkAvailable(VFrame.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }
            val response = chain.proceed(request)
            //网络已连接
            if (NetworkUtil.isNetworkAvailable(VFrame.getContext())) {
                // 有网络时 设置缓存为默认值
                val cacheControl = request.cacheControl().toString()
                response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build()
            } else {
                // 无网络时，设置超时为1周  只对get有用,post没有缓冲
                val maxStale = 60 * 60 * 24 * 7
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .removeHeader("Pragma")
                        .build()
            }
            response
        }
    }



}