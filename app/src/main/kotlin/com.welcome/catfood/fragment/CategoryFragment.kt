package com.welcome.catfood.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.bean.CategoryBean
import com.welcome.catfood.contract.CategoryContract
import com.welcome.catfood.extend.showToast
import com.welcome.catfood.net.exception.ExceptionHandler
import com.welcome.catfood.presenter.CategoryPresenter
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/11
 *     desc   : 分类
 *     version: 1.0
 * </pre>
 */
class CategoryFragment : BaseFragment<CategoryContract.Presenter>(), CategoryContract.View {

    companion object {
        val KEY_TITLE = "key_title"

        fun getInstance(title: String): Fragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            bundle.putString(KEY_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_category

    override fun initView() {
        mLayoutStatusView = multipleStatusView
    }

    override fun getPresenter(): CategoryContract.Presenter? = CategoryPresenter(this)

    override fun lazyLoad() {
        presenterImp?.getCategoryData()
    }

    override fun addCategoryData(categoryList: ArrayList<CategoryBean>) {
        Log.i("wxq", "加载成功")
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
