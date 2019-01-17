package com.welcome.catfood.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.utils.AppUtils
import kotlinx.android.synthetic.main.activity_about.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/17
 *     desc   : 关于 界面
 *     version: 1.0
 * </pre>
 */
class AboutActivity : BaseActivity<IBasePresenter>() {

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, AboutActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_about

    override fun initView() {
        toolbar.setNavigationOnClickListener { finish() }
        tv_version_name.text = "v${AppUtils.getVersionName(this)}"
        relayout_gitHub.setOnClickListener {
            val uri = Uri.parse("https://github.com/wuxiaoqi123/CatFoodMovie")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    override fun startRequest() {
    }
}
