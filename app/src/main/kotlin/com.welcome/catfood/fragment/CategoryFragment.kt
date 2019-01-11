package com.welcome.catfood.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.base.IBasePresenter

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/11
 *     desc   : 分类
 *     version: 1.0
 * </pre>
 */
class CategoryFragment : BaseFragment<IBasePresenter>() {

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
    }

    override fun lazyLoad() {
    }
}
