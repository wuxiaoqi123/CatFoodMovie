package com.welcome.catfood.contract

import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.base.IBaseView
import com.welcome.catfood.bean.HomeBean

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface VideoDetailContract {

    interface View : IBaseView {

        fun setVideo(url: String)

        fun setVideoInfo(itemInfo: HomeBean.Issue.Item)

        fun setBackground(url: String)

        fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>)
    }

    interface Presenter : IBasePresenter {

        fun loadVideoInfo(itemInfo: HomeBean.Issue.Item)

        fun requestRelatedVideo(id: Long)
    }
}