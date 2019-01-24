package com.welcome.catfood.presenter

import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.VideoDetailContract
import com.welcome.catfood.manager.DisplayManager
import com.welcome.catfood.utils.AppUtils

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class VideoDetailPresenter(val mView: VideoDetailContract.View) : VideoDetailContract.Presenter {


    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {
        val playInfo = itemInfo.data?.playInfo
        val isWifi = AppUtils.isWifi()
        if (playInfo!!.size > 1) {
            if (isWifi) {
                playInfo.forEach {
                    if (it.type == "high") {
                        mView.setVideo(it.url)
                        return@forEach
                    }
                }
            } else {
                playInfo.forEach {
                    if (it.type == "normal") {
                        mView.setVideo(it.url)
                        return@forEach
                    }
                }
            }
        } else {
            mView.setVideo(itemInfo.data.playUrl)
        }
        val backgroundUrl =
            itemInfo.data.cover.blurred + "/thumbnail/${DisplayManager.screenHeight - DisplayManager.dp2px(
                250f
            )}x${DisplayManager.screenWidth}"
        backgroundUrl.let { mView.setBackground(it) }
        mView.setVideoInfo(itemInfo)
    }

    override fun requestRelatedVideo(id: Long) {
    }

    override fun cancel() {
    }
}
