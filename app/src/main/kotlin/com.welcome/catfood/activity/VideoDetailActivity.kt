package com.welcome.catfood.activity

import android.content.Intent
import android.os.Build
import android.support.v4.view.ViewCompat
import android.transition.Transition
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.bean.HomeBean
import kotlinx.android.synthetic.main.activity_video_detail.*

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

    private lateinit var itemData: HomeBean.Issue.Item
    private var isTransition = false

    override fun layoutId(): Int = R.layout.activity_video_detail

    override fun initData(intent: Intent?) {
        itemData = intent?.getSerializableExtra(VideoDetailActivity.BUNDLE_VIDEO_DATA)
                as HomeBean.Issue.Item
        isTransition = intent?.getBooleanExtra(VideoDetailActivity.TRANSITION, false)
        saveWatchVideoHistoryInfo(itemData)
    }

    private fun saveWatchVideoHistoryInfo(itemData: HomeBean.Issue.Item) {
        //TODO 保存观看记录
    }

    override fun initView() {
        initTransition()
    }

    private fun initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(mVideoView, IMG_TRANSITION)
            val mTransition = window.sharedElementEnterTransition
            mTransition.addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition) {
                    mTransition.removeListener(this)
                    loadVideoInfo()
                }

                override fun onTransitionResume(transition: Transition?) {
                }

                override fun onTransitionPause(transition: Transition?) {
                }

                override fun onTransitionCancel(transition: Transition?) {
                }

                override fun onTransitionStart(transition: Transition?) {
                }
            })
            startPostponedEnterTransition()
        } else {
            loadVideoInfo()
        }
    }

    private fun loadVideoInfo() {
        //TODO
    }

    override fun startRequest() {
    }
}
