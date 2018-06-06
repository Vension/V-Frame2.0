package com.vension.app.http

import com.vension.app.Constant
import com.vension.app.beans.NewsItemInfo
import com.vension.app.beans.UserInfo
import com.vension.frame.http.BaseResponse
import io.reactivex.Flowable
import retrofit2.http.*


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/29 9:22
 * 描  述：接口服务类
 * ========================================================
 */

interface ApiService {

    @GET(Constant.QQ_SPORT_API)
    fun getQQSportNews(@Query("baid") baid: String, @Query("apikey") apiKey: String): Flowable<BaseResponse<List<NewsItemInfo>>>

    @FormUrlEncoded
    @POST("UserCenter/login")
     fun doLogin(@Field("mobile") mobile: String,
                         @Field("pwd") pwd: String,
                         @Field("ecode") ecode: String): Flowable<BaseResponse<UserInfo>>   //设备识别号
//    /**
//     * 首页
//     */
//    @GET("api/v4/tabs/selected")
//    fun getCategory(): Observable<AndyInfo>
//
//    /**
//     * 获取更多信息
//     * @param url 下一页请求地址
//     */
//    @GET
//    fun getMoreInfo(@Url url: String?): Observable<AndyInfo>
//
//    /**
//     * 获取热门关键词
//     */
//    @GET("api/v3/queries/hot")
//    fun getHotWord(): Observable<MutableList<String>>
//
//    /**
//     * 关键词搜索
//     */
//    @GET("api/v1/search")
//    fun searchVideoByWord(@Query("query") word: String): Observable<AndyInfo>
//
//    /**
//     * 关注
//     */
//    @GET("api/v4/tabs/follow")
//    fun getFollowTabs(): Observable<AndyInfo>
//
//    /**
//     * 发现
//     */
//    @GET("api/v4/tabs/discovery")
//    fun getDiscoveryTab(): Observable<AndyInfo>
//
//    /**
//     * 获取相关视频信息
//     */
//    @GET("api/v4/video/related")
//    fun getRelatedVideo(@Query("id") id: String): Observable<AndyInfo>
//
//    /**
//     * 每日编辑精选
//     */
//    @GET("api/v2/feed?num=3")
//    fun getDailyElite(): Observable<JenniferInfo>
//
//    /**
//     * 每日精选旁的日历显示
//     */
//    @GET("api/v3/issueNavigationList")
//    fun getIssueNaviGationList(): Observable<JenniferInfo>
//

}