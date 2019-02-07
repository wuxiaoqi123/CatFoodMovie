package com.welcome.catfood.model

import com.welcome.catfood.base.BaseModel
import com.welcome.catfood.base.IBaseView
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.net.RetrofitManager
import com.welcome.catfood.net.callback.RxObserver
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
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


    private var nextPageUrl: String? = null

    fun loadHomeData(num: Int, callback: Action<HomeBean>) {
        RetrofitManager.service.getHomeData(num)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(mView.bindToLife())
            .flatMap(object : Function<HomeBean, ObservableSource<HomeBean>> {
                override fun apply(t: HomeBean): ObservableSource<HomeBean> {
                    val bannerItemList = t.issueList[0].itemList
                    bannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        bannerItemList.remove(item)
                    }
                    t.issueList[0].count = t.issueList[0].itemList.size
                    return Observable.just(t)
                }
            })
            .subscribe(
                object : RxObserver<HomeBean>() {
                    override fun onSubscribe(d: Disposable) {
                        mDisposable = d
                    }

                    override fun onFail(code: Int, message: String) {
                        callback.fail(code, message)
                    }

                    override fun onSuccess(t: HomeBean) {
                        nextPageUrl = t.nextPageUrl
                        callback.success(t)
                    }
                })
    }

    fun loadMoreHomeData(callback: Action<ArrayList<HomeBean.Issue.Item>>) {
        RetrofitManager.service.getHomeMoreData(nextPageUrl!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(mView.bindToLife())
            .flatMap(object : Function<HomeBean, ObservableSource<ArrayList<HomeBean.Issue.Item>>> {
                override fun apply(t: HomeBean): ObservableSource<ArrayList<HomeBean.Issue.Item>> {
                    val newItemList = t.issueList[0].itemList
                    newItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        newItemList.remove(item)
                    }
                    nextPageUrl = t.nextPageUrl
                    return Observable.just(newItemList)
                }
            })
            .subscribe(object : RxObserver<ArrayList<HomeBean.Issue.Item>>() {
                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onFail(code: Int, message: String) {
                    callback.fail(code, message)
                }

                override fun onSuccess(t: ArrayList<HomeBean.Issue.Item>) {
                    callback.success(t)
                }
            })
    }
}