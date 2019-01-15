package com.welcome.catfood.activity

import android.app.Activity
import android.content.Intent
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.bean.CategoryBean

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/15
 *     desc   : 分类详情
 *     version: 1.0
 * </pre>
 */
class CategoryDetailActivity : BaseActivity<IBasePresenter>() {

    companion object {
        val KEY_CATEGORY_DATA = "key_category_data"

        fun start(activity: Activity, data: CategoryBean) {
            val intent = Intent(activity, CategoryDetailActivity::class.java)
            intent.putExtra(KEY_CATEGORY_DATA, data)
            activity.startActivity(intent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_categorydetaila

    override fun initView() {
    }

    override fun startRequest() {
    }
}
