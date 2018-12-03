package com.welcome.catfood.fragment

import com.welcome.catfood.R
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.base.IBasePresenter

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/15
 *     desc   : 我的
 *     version: 1.0
 * </pre>
 */
class MineFragment : BaseFragment<IBasePresenter>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
    }

    override fun lazyLoad() {
    }
}
