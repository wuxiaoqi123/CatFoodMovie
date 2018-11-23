package com.welcome.catfood.utils

import android.os.Build

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class AppUtils private constructor() {

    init {
        throw Error("Do not need instantiate!")
    }

    companion object {

        fun getMobileModel(): String {
            var model: String? = Build.MODEL
            model = model?.trim { it <= ' ' } ?: ""
            return model
        }
    }
}
