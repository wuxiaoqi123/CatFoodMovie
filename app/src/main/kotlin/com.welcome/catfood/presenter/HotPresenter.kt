package com.welcome.catfood.presenter

import com.welcome.catfood.bean.TabInfoBean
import com.welcome.catfood.contract.HotContract
import com.welcome.catfood.model.HotModel

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class HotPresenter(val mView: HotContract.View) : HotContract.Presenter {

    private val hotModel: HotModel by lazy { HotModel(mView) }

    override fun loadTabInfo() {
        mView.showLoading()
        hotModel.loadTabInfo(object : HotModel.Callback {
            override fun success(tabInfoBean: TabInfoBean) {
                mView.hideLoading()
                mView.setTabInfo(tabInfoBean)
            }

            override fun fail(code: Int, message: String) {
                mView.hideLoading()
                mView.showErrMsg(code, message)
            }
        })
    }

    override fun cancel() {
        hotModel.cancel()
    }
}
