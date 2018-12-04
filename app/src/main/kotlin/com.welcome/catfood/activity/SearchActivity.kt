package com.welcome.catfood.activity

import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.base.IBasePresenter

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class SearchActivity : BaseActivity<IBasePresenter>() {

    override fun layoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
    }

    override fun startRequest() {
    }
}
