package com.welcome.catfood.base

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
}

interface IBasePresenter {

}
