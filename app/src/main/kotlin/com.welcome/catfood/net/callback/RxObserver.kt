package com.welcome.catfood.net.callback

import com.welcome.catfood.net.exception.ExceptionHandler
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class RxObserver<T> : Observer<T> {

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        val ex = ExceptionHandler.handleException(e)
        onFail(ex.code, ex.message)
    }

    override fun onComplete() {
    }

    abstract fun onFail(code: Int, message: String)

    abstract fun onSuccess(t: T)
}
