package com.welcome.catfood.contract

import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.base.IBaseView
import com.welcome.catfood.bean.HomeBean

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface HomeContract {

    interface View : IBaseView {

        fun setHomeData(homeBean: HomeBean)

        fun addHomeData(itemList: List<HomeBean.Issue.Item>)
    }

    interface Presenter : IBasePresenter {

        fun loadHomeData(num: Int)

        fun loadMoreHomeData()
    }
}
