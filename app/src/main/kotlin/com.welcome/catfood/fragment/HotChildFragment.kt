package com.welcome.catfood.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.welcome.catfood.R
import com.welcome.catfood.adapter.HotChildAdapter
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.HotChildContract
import com.welcome.catfood.extend.showToast
import com.welcome.catfood.net.exception.ExceptionHandler
import com.welcome.catfood.presenter.HotChildPresenter
import kotlinx.android.synthetic.main.fragment_hot_child.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class HotChildFragment : BaseFragment<HotChildContract.Presenter>(), HotChildContract.View {

    companion object {
        fun getInstance(apiUrl: String): Fragment {
            val fragment = HotChildFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.apiUrl = apiUrl
            return fragment
        }
    }

    private var apiUrl: String? = null

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private val mAdapter by lazy {
        activity?.let {
            HotChildAdapter(
                it,
                itemList,
                R.layout.item_hot_child_detail
            )
        }
    }


    override fun getLayoutId(): Int = R.layout.fragment_hot_child

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        mRecyclerView.adapter = mAdapter
        mLayoutStatusView = multipleStatusView
    }

    override fun getPresenter(): HotChildContract.Presenter? {
        return HotChildPresenter(this)
    }

    override fun lazyLoad() {
        if (!apiUrl.isNullOrEmpty()) {
            presenterImp?.loadDataList(apiUrl!!)
        }
    }

    override fun setDataList(itemList: ArrayList<HomeBean.Issue.Item>) {
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
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }
}
