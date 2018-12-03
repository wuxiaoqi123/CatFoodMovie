package com.welcome.catfood.fragment

import com.welcome.catfood.R
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.base.IBasePresenter

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/15
 *     desc   : 发现
 *     version: 1.0
 * </pre>
 */
class FindFragment : BaseFragment<IBasePresenter>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_find
    }

    override fun initView() {
    }

    override fun lazyLoad() {
    }
}
