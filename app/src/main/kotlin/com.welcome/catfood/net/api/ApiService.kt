package com.welcome.catfood.net.api

import com.welcome.catfood.bean.HomeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface ApiService {

    /**
     * 首页精选
     */
    @GET("v2/feed?")
    fun getHomeData(@Query("num") num: Int): Observable<HomeBean>

    /**
     * 根据nextPageUrl获取下一页数据
     */
    @GET
    fun getHomeMoreData(@Url url: String): Observable<HomeBean>
}