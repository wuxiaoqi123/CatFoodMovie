package com.welcome.catfood.ui

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.text.util.Linkify
import com.welcome.catfood.R
import com.welcome.catfood.manager.SettingManager
import kotlinx.android.synthetic.main.menu_project_address.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/02/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class ProjectAddressDialog(context: Context) : BaseMenuDialog(context) {

    override fun getContentView(): Int = R.layout.menu_project_address

    override fun initViews() {
        val spannableString = SpannableString(context.getString(R.string.project_address_url))
        Linkify.addLinks(spannableString, Linkify.ALL)
        spannableString.setSpan(
            ForegroundColorSpan(Color.WHITE),
            0,
            spannableString.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannableString.setSpan(
            UnderlineSpan(),
            0,
            spannableString.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
        menu_project_tv.setText(spannableString)
        menu_project_tv.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun show() {
        super.show()
        menu_address_cardview.setCardBackgroundColor(SettingManager.getThemeColor())
    }
}
