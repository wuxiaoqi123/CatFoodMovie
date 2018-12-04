package com.welcome.catfood.fragment

import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import com.scwang.smartrefresh.header.MaterialHeader
import com.welcome.catfood.R
import com.welcome.catfood.activity.SearchActivity
import com.welcome.catfood.adapter.HomeAdapter
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.HomeContract
import com.welcome.catfood.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/15
 *     desc   : 首页
 *     version: 1.0
 * </pre>
 */
class HomeFragment : BaseFragment<HomeContract.Presenter>(), HomeContract.View {

    private var mTitle: String? = null

    private var num: Int = 1

    private var mHomeAdapter: HomeAdapter? = null

    private var loadingMore = false

    private var isRefresh = false

    private var mMaterialHeader: MaterialHeader? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            loadingMore = false
            presenterImp?.loadHomeData(num)
        }
        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader
        mMaterialHeader?.setShowBezierWave(true)
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        mHeaderSearchImg.setOnClickListener { openSearchActivity() }
    }

    private fun openSearchActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = activity?.let { ActivityOptionsCompat.makeSceneTransitionAnimation(it, mHeaderSearchImg, mHeaderSearchImg.transitionName) }
            startActivity(Intent(activity, SearchActivity::class.java), options?.toBundle())
        } else {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }

    override fun getPresenter(): HomeContract.Presenter? {
        return HomePresenter(this)
    }

    override fun lazyLoad() {
    }

    override fun setHomeData(homeBean: HomeBean) {
    }

    override fun addHomeData(itemList: List<HomeBean.Issue.Item>) {
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showErrMsg(errCode: Int, errMsg: String) {

    }
}
