package com.welcome.catfood.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.FollowContract
import com.welcome.catfood.extend.showToast
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

    override fun getLayoutId(): Int = R.layout.fragment_follow

    override fun initView() {
        mLayoutStatusView = multipleStatusView
    }

    override fun getPresenter(): FollowContract.Presenter? = FollowPresenter(this)

    override fun lazyLoad() {
        presenterImp?.loadData()
    }

    override fun setFollowInfo(data: HomeBean.Issue) {
        Log.i("wxq", "加载成功")
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showErrMsg(errCode: Int, errMsg: String) {
        showToast(errMsg)
    }
}