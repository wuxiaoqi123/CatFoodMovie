package com.welcome.catfood.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.HomeContract
import com.welcome.catfood.presenter.HomePresenter

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

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
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
