package com.welcome.catfood.model

import com.welcome.catfood.base.BaseModel
import com.welcome.catfood.base.IBaseView
import com.welcome.catfood.bean.CategoryBean
import com.welcome.catfood.net.RetrofitManager
import com.welcome.catfood.net.callback.RxObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CategoryModel(view: IBaseView) : BaseModel(view) {


    fun getCategoryData(action: Action<ArrayList<CategoryBean>>) {
        RetrofitManager.service.getCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(mView.bindToLife())
            .subscribe(object : RxObserver<ArrayList<CategoryBean>>() {

                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onFail(code: Int, message: String) {
                    action.fail(code, message)
                }

                override fun onSuccess(t: ArrayList<CategoryBean>) {
                    action.success(t)
                }
            })
    }
}
