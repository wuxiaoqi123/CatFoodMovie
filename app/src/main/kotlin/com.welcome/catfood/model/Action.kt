package com.welcome.catfood.model

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface Action<in T> {

    fun success(data: T)

    fun fail(code: Int, message: String)
}
