package com.welcome.catfood.fragment

import com.welcome.catfood.R
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.bean.TabInfoBean
import com.welcome.catfood.contract.HotContract
import com.welcome.catfood.presenter.HotPresenter

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

    override fun getLayoutId(): Int {
        return R.layout.fragment_hot
    }

    override fun initView() {
    }

    override fun getPresenter(): HotContract.Presenter? = HotPresenter(this)

    override fun lazyLoad() {
        presenterImp?.loadTabInfo()
    }

    override fun setTabInfo(tabInfoBean: TabInfoBean) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showErrMsg(errCode: Int, errMsg: String) {
    }
}
