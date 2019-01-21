package com.welcome.catfood.presenter

import com.welcome.catfood.contract.SearchContract
import com.welcome.catfood.model.Action
import com.welcome.catfood.model.SearchModel

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class SearchPresenter(val mView: SearchContract.View) : SearchContract.Presenter {

    private val searchModel by lazy { SearchModel(mView) }

    override fun requestHotWordData() {
        mView.showLoading()
        searchModel.requestHotWordData(object : Action<ArrayList<String>> {
            override fun fail(code: Int, message: String) {
                mView.hideLoading()
                mView.showErrMsg(code, message)
            }

            override fun success(data: ArrayList<String>) {
                mView.hideLoading()
                mView.setHotWordData(data)
            }
        })
    }

    override fun querySearchData(words: String) {
    }

    override fun loadMoreData() {
    }

    override fun cancel() {
        searchModel.cancel()
    }
}
