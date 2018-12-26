package com.welcome.catfood.presenter

import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.HotChildContract
import com.welcome.catfood.model.HotChildModel
import com.welcome.catfood.net.callback.CommonCallback

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class HotChildPresenter(val mView: HotChildContract.View) : HotChildContract.Presenter {

    private val hotChildModel: HotChildModel by lazy { HotChildModel(mView) }

    override fun loadDataList(apiUrl: String) {
        mView.showLoading()
        hotChildModel.loadData(apiUrl, object : CommonCallback<HomeBean.Issue> {
            override fun fail(code: Int, message: String) {
                mView.hideLoading()
                mView.showErrMsg(code, message)
            }

            override fun success(data: HomeBean.Issue) {
                mView.hideLoading()
                mView.setDataList(data.itemList)
            }
        })
    }

    override fun cancel() {
        hotChildModel.cancel()
    }
}
