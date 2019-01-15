package com.welcome.catfood.bean

import java.io.Serializable

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
data class CategoryBean(
    val id: Long, val name: String, val description: String,
    val bgPicture: String, val bgColor: String,
    val headerImage: String
) : Serializable
