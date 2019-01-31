package com.welcome.catfood.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.welcome.catfood.R
import com.welcome.catfood.adapter.CategoryDetailAdapter
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.bean.CategoryBean
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.CategoryDetailContract
import com.welcome.catfood.presenter.CategoryDetailPresenter
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
class CategoryDetailActivity : BaseActivity<CategoryDetailContract.Presenter>(),
    CategoryDetailContract.View {

    companion object {
        val KEY_CATEGORY_DATA = "key_category_data"

        fun start(activity: Activity, data: CategoryBean) {
            val intent = Intent(activity, CategoryDetailActivity::class.java)
            intent.putExtra(KEY_CATEGORY_DATA, data)
            activity.startActivity(intent)
        }
    }

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private val mAdapter by lazy {
        CategoryDetailAdapter(
            this,
            itemList,
            R.layout.item_category_detail
        )
    }

    private var mCategoryData: CategoryBean? = null

    private var loadingMore = false

    override fun layoutId(): Int = R.layout.activity_categorydetaila

    override fun initData(intent: Intent?) {
        mCategoryData = intent?.getSerializableExtra(KEY_CATEGORY_DATA) as CategoryBean?
    }

    @SuppressLint("SetTextI18n")
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

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager.itemCount
                val lastVisibleItem =
                    (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true
                    presenterImp?.loadMoreData()
                }
            }
        })
    }

    override fun getPresenter(): CategoryDetailContract.Presenter = CategoryDetailPresenter(this)

    override fun startRequest() {
        mCategoryData?.id?.let {
            presenterImp?.getCategoryDetailList(it)
        }
    }

    override fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mAdapter.addData(itemList)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showErrMsg(errCode: Int, errMsg: String) {
        multipleStatusView.showError()
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show()
    }
}
