package com.welcome.catfood.presenter

import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.FollowContract
import com.welcome.catfood.model.FollowModel

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class FollowPresenter(private val mView: FollowContract.View) : FollowContract.Presenter {

    private val followModel: FollowModel by lazy { FollowModel(mView) }

    override fun loadData() {
        mView.showLoading()
        followModel.loadData(object : FollowModel.CallbackLoad {
            override fun success(t: HomeBean.Issue) {
                mView.hideLoading()
                mView.setFollowInfo(t)
            }

            override fun fail(code: Int, message: String) {
                mView.hideLoading()
                mView.showErrMsg(code, message)
            }
        })
    }

    override fun loadMoreData() {
        mView.showLoading()
        followModel.loadMoreData(object : FollowModel.CallbackLoad {
            override fun success(t: HomeBean.Issue) {
                mView.hideLoading()
                mView.setFollowInfo(t)
            }

            override fun fail(code: Int, message: String) {
                mView.hideLoading()
                mView.showErrMsg(code, message)
            }
        })
    }

    override fun cancel() {
        followModel.cancel()
    }
}
