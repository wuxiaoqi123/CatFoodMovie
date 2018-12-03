package com.welcome.catfood.extend

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import com.welcome.catfood.utils.ToastUtil

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/08
 *     desc   :
 *     version: 1.0
 * </pre>
 */

fun Activity.getVersionName(): String {
    val packageInfo = packageManager.getPackageInfo(packageName, 0)
    return packageInfo.versionName
}

fun View.dp2px(value: Float): Float {
    val density = Resources.getSystem().displayMetrics.density
    return (value * density + 0.5f)
}

fun Context.showToast(msg: String) {
    ToastUtil.show(msg)
}

fun durationFormat(duration: Long): String {
    val minute = duration / 60
    val second = duration % 60
    return if (second <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}

