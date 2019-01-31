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
 *     time   : 2019/01/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CategoryDetailModel(view: IBaseView) : BaseModel(view) {

    private var nextPageUrl: String? = null

    fun getCategoryDetailList(id: Long, action: Action<ArrayList<HomeBean.Issue.Item>>) {
        RetrofitManager.service.getCategoryDetailList(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(mView.bindToLife())
            .subscribe(object : RxObserver<HomeBean.Issue>() {

                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onFail(code: Int, message: String) {
                    action.fail(code, message)
                }

                override fun onSuccess(t: HomeBean.Issue) {
                    nextPageUrl = t.nextPageUrl
                    action.success(t.itemList)
                }
            })
    }

    fun loadMoreData(action: Action<ArrayList<HomeBean.Issue.Item>>) {
        nextPageUrl?.let {
            RetrofitManager.service.getIssueData(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.bindToLife())
                .subscribe(object : RxObserver<HomeBean.Issue>() {

                    override fun onSubscribe(d: Disposable) {
                        mDisposable = d
                    }

                    override fun onFail(code: Int, message: String) {
                        action.fail(code, message)
                    }

                    override fun onSuccess(t: HomeBean.Issue) {
                        nextPageUrl = t.nextPageUrl
                        action.success(t.itemList)
                    }
                })
        }
    }
}
