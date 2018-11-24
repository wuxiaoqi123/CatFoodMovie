package com.welcome.catfood.model

import com.welcome.catfood.base.BaseModel
import com.welcome.catfood.base.IBaseView
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.net.RetrofitManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class HomeModel(view: IBaseView) : BaseModel(view) {

    fun loadHomeData(num: Int): Observable<HomeBean> =
        RetrofitManager.service.getHomeData(num)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(mView.bindToLife())

    fun loadMoreHomeData(url: String) =
        RetrofitManager.service.getHomeMoreData(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(mView.bindToLife())
}