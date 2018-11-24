package com.welcome.catfood.base

import io.reactivex.disposables.Disposable

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class BaseModel(view: IBaseView) : IBaseModel {

    protected val mView: IBaseView = view

    var mDisposable: Disposable? = null

    override fun cancel() {
        if (mDisposable?.isDisposed == true) {
            mDisposable?.dispose()
        }
        mDisposable = null
    }
}
