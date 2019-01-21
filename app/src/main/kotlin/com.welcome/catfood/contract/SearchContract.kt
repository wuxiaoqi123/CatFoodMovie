package com.welcome.catfood.contract

import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.base.IBaseView
import com.welcome.catfood.bean.HomeBean

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface SearchContract {

    interface View : IBaseView {

        fun setHotWordData(string: ArrayList<String>)

        fun setSearchResult(issue: HomeBean.Issue)

        fun setEmptyView()
    }

    interface Presenter : IBasePresenter {

        fun requestHotWordData()

        fun querySearchData(words: String)

        fun loadMoreData()
    }
}
