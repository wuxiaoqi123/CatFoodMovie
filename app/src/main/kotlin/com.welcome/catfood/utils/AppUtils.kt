package com.welcome.catfood.utils

import android.content.res.Resources
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

        fun dp2px(value: Float): Float {
            val density = Resources.getSystem().displayMetrics.density
            return (value * density + 0.5f)
        }
    }
}
