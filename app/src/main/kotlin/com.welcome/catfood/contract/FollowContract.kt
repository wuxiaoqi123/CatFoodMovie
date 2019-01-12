package com.welcome.catfood.contract

import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.base.IBaseView
import com.welcome.catfood.bean.HomeBean

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface FollowContract {

    interface View : IBaseView {

        fun setFollowInfo(data: HomeBean.Issue)
    }

    interface Presenter : IBasePresenter {

        fun loadData()

        fun loadMoreData()
    }
}
