package com.welcome.catfood.presenter

import com.welcome.catfood.bean.CategoryBean
import com.welcome.catfood.contract.CategoryContract
import com.welcome.catfood.model.Action
import com.welcome.catfood.model.CategoryModel

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CategoryPresenter(val mView: CategoryContract.View) : CategoryContract.Presenter {

    private val mCategoryModel by lazy { CategoryModel(mView) }

    override fun getCategoryData() {
        mView.showLoading()
        mCategoryModel.getCategoryData(object : Action<ArrayList<CategoryBean>> {
            override fun success(data: ArrayList<CategoryBean>) {
                mView.hideLoading()
                mView.addCategoryData(data)
            }

            override fun fail(code: Int, message: String) {
                mView.hideLoading()
                mView.showErrMsg(code, message)
            }
        })
    }

    override fun cancel() {
        mCategoryModel.cancel()
    }
}
