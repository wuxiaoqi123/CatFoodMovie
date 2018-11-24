package com.welcome.catfood.base

import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface IBaseView {

    fun showLoading()

    fun hideLoading()

    fun showErrMsg(errCode: Int = 0, errMsg: String = "")

    fun <T> bindToLife(): LifecycleTransformer<T>
}

interface IBasePresenter {

}

interface IBaseModel {

    fun cancel()
}
