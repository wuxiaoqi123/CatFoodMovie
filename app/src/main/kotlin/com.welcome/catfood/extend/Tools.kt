package com.welcome.catfood.extend

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import com.welcome.catfood.util.ToastUtil

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

