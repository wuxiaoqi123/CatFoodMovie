package com.welcome.catfood.model

import com.welcome.catfood.base.BaseModel
import com.welcome.catfood.base.IBaseView
import com.welcome.catfood.bean.TabInfoBean
import com.welcome.catfood.net.RetrofitManager
import com.welcome.catfood.net.callback.RxObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class HotModel(val view: IBaseView) : BaseModel(view) {

    fun loadTabInfo(callback: Action<TabInfoBean>) {
        RetrofitManager.service.getRankList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(view.bindToLife())
            .subscribe(object : RxObserver<TabInfoBean>() {

                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onFail(code: Int, message: String) {
                    callback.fail(code, message)
                }

                override fun onSuccess(t: TabInfoBean) {
                    callback.success(t)
                }
            })
    }
}
