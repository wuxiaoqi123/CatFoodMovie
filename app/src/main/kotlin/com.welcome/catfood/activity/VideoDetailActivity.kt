package com.welcome.catfood.activity

import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.base.IBasePresenter

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/20
 *     desc   : 视频页
 *     version: 1.0
 * </pre>
 */
class VideoDetailActivity : BaseActivity<IBasePresenter>() {

    companion object {
        const val BUNDLE_VIDEO_DATA = "video_data"
        const val IMG_TRANSITION = "img_transition"
        const val TRANSITION = "transition"
    }

    override fun layoutId(): Int = R.layout.activity_video_detail

    override fun initView() {
    }

    override fun startRequest() {
    }
}
