package com.welcome.catfood.presenter

import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.HomeContract
import com.welcome.catfood.model.Action
import com.welcome.catfood.model.HomeModel

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class HomePresenter(view: HomeContract.View) : HomeContract.Presenter {
    private val mView: HomeContract.View;

    init {
        this.mView = view
    }

    private val homeModel: HomeModel by lazy { HomeModel(mView) }

    override fun loadHomeData(num: Int) {
        mView.showLoading()
        homeModel.loadHomeData(num, object : Action<HomeBean> {

            override fun success(data: HomeBean) {
                mView.hideLoading()
                mView.setHomeData(data)
                loadMoreHomeData()
            }

            override fun fail(code: Int, message: String) {
                mView.hideLoading()
                mView.showErrMsg(code, message)
            }
        })
    }

    override fun loadMoreHomeData() {
//        mView.showLoading()
        homeModel.loadMoreHomeData(object : Action<ArrayList<HomeBean.Issue.Item>> {
            override fun success(data: ArrayList<HomeBean.Issue.Item>) {
//                mView.hideLoading()
                mView.addHomeData(data)
            }

            override fun fail(code: Int, message: String) {
//                mView.hideLoading()
                mView.showErrMsg(code, message)
            }
        })
    }

    override fun cancel() {
        homeModel.cancel()
    }
}
