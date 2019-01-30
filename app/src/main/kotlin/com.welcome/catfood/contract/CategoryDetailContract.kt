package com.welcome.catfood.contract

import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.base.IBaseView
import com.welcome.catfood.bean.HomeBean

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface CategoryDetailContract {

    interface View : IBaseView {

        fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>)
    }

    interface Presenter : IBasePresenter {

        fun getCategoryDetailList(id: Long)

        fun loadMoreData()
    }
}