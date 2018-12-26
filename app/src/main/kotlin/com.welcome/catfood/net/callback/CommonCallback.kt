package com.welcome.catfood.net.callback

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface CommonCallback<in T> {

    fun fail(code: Int, message: String)

    fun success(data: T)
}