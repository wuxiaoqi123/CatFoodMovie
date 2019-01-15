package com.welcome.catfood.contract

import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.base.IBaseView
import com.welcome.catfood.bean.CategoryBean

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface CategoryContract {

    interface View : IBaseView {

        fun addCategoryData(categoryList: ArrayList<CategoryBean>)
    }

    interface Presenter : IBasePresenter {

        fun getCategoryData()
    }
}