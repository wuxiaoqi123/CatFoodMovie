package com.welcome.catfood.contract

import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.base.IBaseView
import com.welcome.catfood.bean.HomeBean

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface HotChildContract {

    interface View : IBaseView {

        fun setDataList(itemList: ArrayList<HomeBean.Issue.Item>)
    }

    interface Presenter : IBasePresenter {

        fun loadDataList(apiUrl: String)
    }
}
