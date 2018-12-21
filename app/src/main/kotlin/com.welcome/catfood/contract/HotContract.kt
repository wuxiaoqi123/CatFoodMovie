package com.welcome.catfood.contract

import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.base.IBaseView
import com.welcome.catfood.bean.TabInfoBean

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface HotContract {

    interface View : IBaseView {

        fun setTabInfo(tabInfoBean: TabInfoBean)
    }

    interface Presenter : IBasePresenter {

        fun loadTabInfo()
    }
}
