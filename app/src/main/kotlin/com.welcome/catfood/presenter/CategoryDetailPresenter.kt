package com.welcome.catfood.presenter

import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.CategoryDetailContract
import com.welcome.catfood.model.Action
import com.welcome.catfood.model.CategoryDetailModel

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CategoryDetailPresenter(val mView: CategoryDetailContract.View) :
    CategoryDetailContract.Presenter {

    private val mCategoryDetailModel by lazy { CategoryDetailModel(mView) }

    override fun getCategoryDetailList(id: Long) {
        mCategoryDetailModel.getCategoryDetailList(id,
            object : Action<ArrayList<HomeBean.Issue.Item>> {
                override fun success(data: ArrayList<HomeBean.Issue.Item>) {
                    mView.setCateDetailList(data)
                }

                override fun fail(code: Int, message: String) {
                    mView.showErrMsg(code, message)
                }
            })
    }

    override fun loadMoreData() {
        mCategoryDetailModel.loadMoreData(object : Action<ArrayList<HomeBean.Issue.Item>> {
            override fun success(data: ArrayList<HomeBean.Issue.Item>) {
                mView.setCateDetailList(data)
            }

            override fun fail(code: Int, message: String) {
                mView.showErrMsg(code, message)
            }
        })
    }

    override fun cancel() {
        mCategoryDetailModel.cancel()
    }
}
