package com.welcome.catfood.utils

import android.view.Gravity
import android.widget.Toast
import com.welcome.catfood.app.CatFoodApplication
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
object ToastUtil {

    const val show = Config.TOAST_SHOW

    fun show(text: String, duration: Int = Toast.LENGTH_SHORT) {
        if (show) Toast.makeText(CatFoodApplication.context, text, duration).show()
    }

    fun showCenter(text: String, duration: Int = Toast.LENGTH_SHORT) {
        if (show) {
            val makeText = Toast.makeText(CatFoodApplication.context, text, duration)
            makeText.setGravity(Gravity.CENTER, 0, 0)
            makeText.show()
        }
    }
}
