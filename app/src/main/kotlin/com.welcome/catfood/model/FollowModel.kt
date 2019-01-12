package com.welcome.catfood.model

import com.welcome.catfood.base.BaseModel
import com.welcome.catfood.base.IBaseView
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.net.RetrofitManager
import com.welcome.catfood.net.callback.RxObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class FollowModel(view: IBaseView) : BaseModel(view) {

    private var nextPageUrl: String? = null

    fun loadData(callback: CallbackLoad) {
        RetrofitManager.service.getFollowInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RxObserver<HomeBean.Issue>() {

                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onFail(code: Int, message: String) {
                    callback.fail(code, message)
                }

                override fun onSuccess(t: HomeBean.Issue) {
                    nextPageUrl = t.nextPageUrl
                    callback.success(t)
                }
            })
    }

    fun loadMoreData(callback: CallbackLoad) {
        nextPageUrl?.let {
            RetrofitManager.service.getIssueData(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RxObserver<HomeBean.Issue>() {

                    override fun onSubscribe(d: Disposable) {
                        mDisposable = d
                    }

                    override fun onFail(code: Int, message: String) {
                        callback.fail(code, message)
                    }

                    override fun onSuccess(t: HomeBean.Issue) {
                        nextPageUrl = t.nextPageUrl
                        callback.success(t)
                    }
                })
        }
    }

    interface CallbackLoad {
        fun success(t: HomeBean.Issue)

        fun fail(code: Int, message: String)
    }
}
