package com.welcome.catfood.util

import android.util.Log
import com.welcome.catfood.config.Config

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */
object LogUtil {

    const val show = Config.TOAST_SHOW

    fun i(tag: String = Config.TAG, msg: String) {
        if (show) Log.i(tag, msg)
    }

    fun v(tag: String = Config.TAG, msg: String) {
        if (show) Log.v(tag, msg)
    }

    fun e(tag: String = Config.TAG, msg: String) {
        if (show) Log.e(tag, msg)
    }

    fun w(tag: String = Config.TAG, msg: String) {
        if (show) Log.w(tag, msg)
    }
}
