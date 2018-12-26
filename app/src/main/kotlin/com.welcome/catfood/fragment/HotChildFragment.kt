package com.welcome.catfood.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.HotChildContract
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

    private var apiUrl: String? = null

    companion object {
        fun getInstance(apiUrl: String): Fragment {
            val fragment = HotChildFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.apiUrl = apiUrl
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_hot_child

    override fun initView() {
        mLayoutStatusView = multipleStatusView
    }

    override fun getPresenter(): HotChildContract.Presenter? {
        return HotChildPresenter(this)
    }

    override fun lazyLoad() {
        presenterImp?.loadDataList(apiUrl!!)
    }

    override fun setDataList(itemList: ArrayList<HomeBean.Issue.Item>) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showErrMsg(errCode: Int, errMsg: String) {
    }
}
