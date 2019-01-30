package com.welcome.catfood.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.bean.CategoryBean
import kotlinx.android.synthetic.main.activity_categorydetaila.*

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

    private var mCategoryData: CategoryBean? = null

    override fun layoutId(): Int = R.layout.activity_categorydetaila

    override fun initData(intent: Intent?) {
        mCategoryData = intent?.getSerializableExtra(KEY_CATEGORY_DATA) as CategoryBean?
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        Glide.with(this)
            .load(mCategoryData?.headerImage)
            .apply(RequestOptions().placeholder(R.color.color_darker_gray))
            .into(imageView)
        tv_category_desc.text = "#${mCategoryData?.description}#"
        collapsing_toolbar_layout.title = mCategoryData?.name
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE)
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)
    }

    override fun startRequest() {
    }
}
