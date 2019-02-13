package com.welcome.catfood.activity

import android.os.Build
import android.widget.LinearLayout
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.extend.getStatusBarHeight
import kotlinx.android.synthetic.main.activity_personal_homepage.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/02/13
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class PersonalHomepageActivity : BaseActivity<IBasePresenter>() {

    override fun layoutId(): Int = R.layout.activity_personal_homepage

    override fun initView() {
        initStatusBar()
    }

    private fun initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val lp = LinearLayout.LayoutParams(-1, getStatusBarHeight())
            mStatusBarView.layoutParams = lp
            mStatusBarView.requestLayout()
        }
    }

    override fun startRequest() {
    }
}
