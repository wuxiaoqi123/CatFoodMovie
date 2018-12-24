package com.welcome.catfood.fragment

import com.welcome.catfood.R
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.bean.TabInfoBean
import com.welcome.catfood.contract.HotContract
import com.welcome.catfood.extend.showToast
import com.welcome.catfood.net.exception.ExceptionHandler
import com.welcome.catfood.presenter.HotPresenter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/15
 *     desc   : 热门
 *     version: 1.0
 * </pre>
 */
class HotFragment : BaseFragment<HotContract.Presenter>(), HotContract.View {

    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun initView() {
        mLayoutStatusView = multipleStatusView
    }

    override fun getPresenter(): HotContract.Presenter? = HotPresenter(this)

    override fun lazyLoad() {
        presenterImp?.loadTabInfo()
    }

    override fun setTabInfo(tabInfoBean: TabInfoBean) {
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
