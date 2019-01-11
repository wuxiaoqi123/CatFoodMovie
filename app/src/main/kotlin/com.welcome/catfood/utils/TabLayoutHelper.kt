package com.welcome.catfood.utils

import android.support.design.widget.TabLayout
import android.widget.LinearLayout
import java.lang.reflect.Field

object TabLayoutHelper {

    fun setUpIndicorWidth(tabLayout: TabLayout) {
        val tabLayoutClass = tabLayout.javaClass
        var tabStrip: Field? = null
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip")
            tabStrip.isAccessible = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        var layout: LinearLayout? = null
        try {
            layout = tabStrip?.get(tabLayout) as LinearLayout
            for (i in 0 until layout!!.childCount) {
                val child = layout.getChildAt(i)
                child.setPadding(0, 0, 0, 0)
                val params =
                    LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                params.marginStart = AppUtils.dp2px(50f).toInt()
                params.marginEnd = AppUtils.dp2px(50f).toInt()
                child.layoutParams = params
                child.invalidate()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}