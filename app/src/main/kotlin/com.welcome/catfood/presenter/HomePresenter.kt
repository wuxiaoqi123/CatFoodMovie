package com.welcome.catfood.presenter

import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.HomeContract
import com.welcome.catfood.model.HomeModel
import com.welcome.catfood.net.exception.ExceptionHandler

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

    private var bannerHomeBean: HomeBean? = null

    private var nextPageUrl: String? = null

    private val homeModel: HomeModel by lazy { HomeModel(mView) }

    override fun loadHomeData(num: Int) {
        mView.showLoading()
        val subscribe = homeModel.loadHomeData(num)
            .flatMap { homeBean ->
                val bannerItemList = homeBean.issueList[0].itemList
                bannerItemList.filter { item ->
                    item.type == "banner2" || item.type == "horizontalScrollCard"
                }.forEach { item ->
                    bannerItemList.remove(item)
                }
                bannerHomeBean = homeBean
                homeModel.loadMoreHomeData(homeBean.nextPageUrl)
            }.subscribe({ homeBean ->
                mView.apply {
                    hideLoading()
                    nextPageUrl = homeBean.nextPageUrl
                    val newBannerItemList = homeBean.issueList[0].itemList
                    newBannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        newBannerItemList.remove(item)
                    }
                    bannerHomeBean!!.issueList[0].count =
                            bannerHomeBean!!.issueList[0].itemList.size
                    bannerHomeBean?.issueList!![0].itemList.addAll(newBannerItemList)
                    setHomeData(bannerHomeBean!!)
                }
            }, { t ->
                mView.apply {
                    hideLoading()
                    showErrMsg(
                        ExceptionHandler.handleException(t).code,
                        ExceptionHandler.handleException(t).message
                    )
                }
            })
    }

    override fun loadMoreHomeData() {
        val disposable = nextPageUrl?.let {
            homeModel.loadMoreHomeData(it)
                .subscribe({ homeBean ->
                    mView.apply {
                        val newItemList = homeBean.issueList[0].itemList
                        newItemList.filter { item ->
                            item.type == "banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            newItemList.remove(item)
                        }
                        nextPageUrl = homeBean.nextPageUrl
                        hideLoading()
                        addHomeData(newItemList)
                    }
                }, { t ->
                    mView.apply {
                        hideLoading()
                        val ex = ExceptionHandler.handleException(t)
                        showErrMsg(ex.code, ex.message)
                    }
                })
        }
    }

    override fun cancel() {
        homeModel.cancel()
    }
}
