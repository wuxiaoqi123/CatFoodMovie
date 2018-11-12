package com.welcome.catfood.extend

import android.app.Activity

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

