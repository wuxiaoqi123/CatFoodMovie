package com.welcome.catfood.manager

import android.content.res.Resources
import android.util.DisplayMetrics

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
object DisplayManager {

    const val UI_WIDTH = 1080
    const val UI_HEIGHT = 1920

    private var displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics

    var screenWidth: Int = -1
        get() = if (field == -1) UI_WIDTH else field

    var screenHeight: Int = -1
        get() = if (field == -1) UI_HEIGHT else field

    var screenDpi: Int = -1
        get() = field

    init {
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels
        screenDpi = displayMetrics.densityDpi
    }

    fun dp2px(dpValue: Float): Int {
        val scale = displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun getRealHeight(px: Int, parentHeight: Float = UI_HEIGHT.toFloat()): Int {
        return (px / parentHeight * screenHeight).toInt()
    }

    fun getRealWidth(px: Int, parentWidth: Float = UI_WIDTH.toFloat()): Int {
        return (px / parentWidth * screenWidth).toInt()
    }
}
