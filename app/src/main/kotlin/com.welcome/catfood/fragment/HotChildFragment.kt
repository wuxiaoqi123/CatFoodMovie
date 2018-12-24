package com.welcome.catfood.fragment

import android.support.v4.app.Fragment
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.base.IBasePresenter

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class HotChildFragment : BaseFragment<IBasePresenter>() {

    companion object {
        fun getInstance(): Fragment {
            return HotChildFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_hot_child

    override fun initView() {
    }

    override fun lazyLoad() {
    }
}
