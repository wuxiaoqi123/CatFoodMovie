package com.welcome.catfood.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.welcome.catfood.R
import com.welcome.catfood.adapter.FollowAdapter
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.FollowContract
import com.welcome.catfood.extend.showToast
import com.welcome.catfood.net.exception.ExceptionHandler
import com.welcome.catfood.presenter.FollowPresenter
import kotlinx.android.synthetic.main.fragment_follow.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/11
 *     desc   : 关注
 *     version: 1.0
 * </pre>
 */
class FollowFragment : BaseFragment<FollowContract.Presenter>(), FollowContract.View {

    companion object {

        val KEY_TITLE = "key_title"

        fun getInstance(title: String): Fragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            bundle.putString(KEY_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private var loadingMore = false

    private val mAdapter by lazy { activity?.let { FollowAdapter(it, itemList) } }

    override fun getLayoutId(): Int = R.layout.fragment_follow

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
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

    override fun getPresenter(): FollowContract.Presenter? = FollowPresenter(this)

    override fun lazyLoad() {
        presenterImp?.loadData()
    }

    override fun setFollowInfo(data: HomeBean.Issue) {
        loadingMore = false
        itemList = data.itemList
        mAdapter?.addData(itemList)
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun hideLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun showErrMsg(errCode: Int, errMsg: String) {
        showToast(errMsg)
        if (errCode == ExceptionHandler.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }
}