package com.welcome.catfood.presenter

import com.welcome.catfood.contract.HomeContract

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class HomePresenter(view: HomeContract.View) : HomeContract.Presenter {

    private val mView: HomeContract.View;

    private var nextPageUrl: String? = null



    init {
        this.mView = view
    }

    override fun loadHomeData(num: Int) {

    }

    override fun loadMoreHomeData() {
    }
}
