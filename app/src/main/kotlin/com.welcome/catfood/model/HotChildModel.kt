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
 *     time   : 2018/12/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class HotChildModel(mView: IBaseView) : BaseModel(mView) {

    fun loadData(apiUrl: String, callback: Action<HomeBean.Issue>) {
        RetrofitManager.service.getIssueData(apiUrl)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(mView.bindToLife())
            .subscribe(object : RxObserver<HomeBean.Issue>() {

                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onFail(code: Int, message: String) {
                    callback.fail(code, message)
                }

                override fun onSuccess(t: HomeBean.Issue) {
                    callback.success(t)
                }
            })
    }
}
