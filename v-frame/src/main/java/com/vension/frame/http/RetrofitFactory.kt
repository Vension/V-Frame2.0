package com.vension.frame.http

import com.google.gson.GsonBuilder
import com.vension.frame.VFrame
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/23 16:54
 * 描  述：网络工厂
 * ========================================================
 */

object RetrofitFactory {

    var base_url = "https://github.com/vension/"//默认baseUrl
    private lateinit var mRetrofit: Retrofit

    @Synchronized
    fun createRetrofit(): Retrofit {
        return createRetrofit(base_url)
    }

    /**
     * 创建Retrofit实例
     */
    @Synchronized
    fun createRetrofit(url : String): Retrofit {
//        if (mRetrofit == null) {
//            synchronized(RetrofitFactory::class.java) {
//                if (mRetrofit == null) {
        val mClient = createHttpClient()
        // 创建retrofit的实例
        mRetrofit = Retrofit.Builder()
                .baseUrl(url)  //设置BaseUrl
                .client(mClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// //Retrofit2与Rxjava2之间结合的适配器
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))// 增加返回值为Gson实体的支持，不会进行解密等处理
                .addConverterFactory(ScalarsConverterFactory.create())// 增加返回值为String的支持
                .build()
//                }
//            }
//        }
        return mRetrofit
    }

    /**
     * 创建OkHttpClient实例
     */
    private fun createHttpClient(): OkHttpClient? {
        //设置 请求的缓存的大小跟位置
        val cacheFile = File(VFrame.getContext().cacheDir, "HttpCache")
        val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

        // Cookie 持久化
        //val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(InitApp.AppContext))

        val mClient = OkHttpClient.Builder()
                //                            .cookieJar(cookieJar)
                //                            .addInterceptor(addQueryParameterInterceptor())  //公共参数添加
                .addInterceptor(InterceptorUtil.addHeaderParamInterceptor()) //自定义的拦截器，用于添加公共参数
                .addInterceptor(InterceptorUtil.initLogInterceptor())//拦截器，用于日志的打印
                .addInterceptor(InterceptorUtil.addCacheInterceptor()) //添加网络缓存拦截
                .cache(cache)  //添加缓存
                .connectTimeout(30L, TimeUnit.SECONDS)//设置超时
                .readTimeout(30L, TimeUnit.SECONDS)
                .writeTimeout(30L, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool())
                .retryOnConnectionFailure(true)//重连
//                .sslSocketFactory(RqbTrustManager.getInstance().getSSLSocketFactory("BKS", R.raw.rqb_ssl))// 设置证书
                .build()
        return mClient
    }


    fun initConfig(baseUrl: String){
        this.base_url = baseUrl
    }


    //===========================
    /*@Synchronized
    fun <S> createService(serviceClass: Class<S>): S {
        return createService(serviceClass, "")
    }

    fun <S> createService(serviceClass: Class<S>, baseUrl: String): S {
        // Retrofit要求baseUrl以 '/' 为结尾
        val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        if (!TextUtils.isEmpty(baseUrl)) {
            retrofitBuilder.baseUrl(baseUrl)
        } else {
            retrofitBuilder.baseUrl(base_url)
        }

        val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
//        clientBuilder.interceptors().clear()
//        clientBuilder.interceptors().add(interceptor)

        val client = clientBuilder.build()
        val retrofit = retrofitBuilder.client(client).build()
        return retrofit.create(serviceClass)
    }*/


}