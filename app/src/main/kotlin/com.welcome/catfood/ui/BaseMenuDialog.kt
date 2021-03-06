package com.welcome.catfood.ui

import android.app.Dialog
import android.content.Context
import android.support.annotation.LayoutRes
import com.welcome.catfood.R

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/02/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */

@Suppress("LeakingThis")
abstract class BaseMenuDialog : Dialog {

    protected var mContext: Context? = null

    init {
        mContext = context
    }

    constructor(context: Context) : this(context, R.style.menu_dialog)

    constructor(context: Context, themeRes: Int) : super(context, themeRes) {
        setContentView(getContentView())
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        initViews()
    }

    @LayoutRes
    abstract fun getContentView(): Int

    abstract fun initViews()
}
