package com.welcome.catfood.ui

import android.content.Context
import com.welcome.catfood.R
import com.welcome.catfood.manager.SettingManager
import kotlinx.android.synthetic.main.menu_about.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/02/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class AboutDialog(context: Context) : BaseMenuDialog(context) {

    override fun getContentView(): Int = R.layout.menu_about

    override fun initViews() {
        menu_about_tv.setText(context.getString(R.string.about_app))
    }

    override fun show() {
        super.show()
        menu_about_cardview.setCardBackgroundColor(SettingManager.getThemeColor())
    }
}
