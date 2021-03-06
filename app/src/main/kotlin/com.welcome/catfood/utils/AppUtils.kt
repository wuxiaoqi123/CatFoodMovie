package com.welcome.catfood.utils

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.os.Build
import com.welcome.catfood.app.CatFoodApplication

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

        fun getVersionName(context: Context): String {
            return with(context) {
                val packageInfo = packageManager.getPackageInfo(packageName, 0)
                packageInfo.versionName
            }
        }

        @JvmStatic
        fun isWifi(): Boolean {
            val connectivityManager =
                CatFoodApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
        }
    }
}
