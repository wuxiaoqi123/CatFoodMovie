package com.welcome.catfood.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.support.v4.view.ViewCompat
import android.transition.Transition
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.VideoDetailContract
import com.welcome.catfood.extend.showToast
import com.welcome.catfood.presenter.VideoDetailPresenter
import com.welcome.catfood.ui.VideoListener
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
class VideoDetailActivity : BaseActivity<VideoDetailContract.Presenter>(),
    VideoDetailContract.View {

    companion object {
        const val BUNDLE_VIDEO_DATA = "video_data"
        const val IMG_TRANSITION = "img_transition"
        const val TRANSITION = "transition"
    }

    private lateinit var itemData: HomeBean.Issue.Item
    private var isTransition = false

    private var orientationUtls: OrientationUtils? = null
    private var isPlay = false
    private var isPause = false

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
        initVideoViewConfig()
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

    private fun initVideoViewConfig() {
        orientationUtls = OrientationUtils(this, mVideoView)
        mVideoView.isRotateViewAuto = false
        mVideoView.setIsTouchWiget(true)

        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this)
            .load(itemData.data?.cover?.feed)
            .apply(RequestOptions().centerCrop())
            .into(imageView)
        mVideoView.thumbImageView = imageView
        mVideoView.setStandardVideoAllCallBack(object : VideoListener {
            override fun onPrepared(url: String?, vararg objects: Any?) {
                orientationUtls?.isEnable = true
                isPlay = true
            }

            override fun onAutoComplete(url: String?, vararg objects: Any?) {
                super.onAutoComplete(url, *objects)
            }

            override fun onPlayError(url: String?, vararg objects: Any?) {
                showToast("播放出错，请重试")
            }

            override fun onEnterFullscreen(url: String?, vararg objects: Any?) {
                super.onEnterFullscreen(url, *objects)
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                orientationUtls?.backToProtVideo()
            }
        })
        mVideoView.backButton.setOnClickListener { onBackPressed() }
        mVideoView.fullscreenButton.setOnClickListener {
            orientationUtls?.resolveByClick()
            mVideoView.startWindowFullscreen(this, true, true)
        }
        mVideoView.setLockClickListener { _, lock ->
            orientationUtls?.isEnable = !lock
        }
    }

    private fun loadVideoInfo() {
        presenterImp?.loadVideoInfo(itemData)
    }

    override fun startRequest() {
    }

    override fun getPresenter(): VideoDetailContract.Presenter = VideoDetailPresenter(this)

    override fun setVideo(url: String) {
        mVideoView.setUp(url, false, "")
        mVideoView.startPlayLogic()
    }

    override fun setVideoInfo(itemInfo: HomeBean.Issue.Item) {
        itemData = itemInfo

    }

    override fun setBackground(url: String) {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions().centerCrop().format(DecodeFormat.PREFER_ARGB_8888))
            .transition(DrawableTransitionOptions().crossFade())
            .into(mVideoBackground)
    }

    override fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showErrMsg(errCode: Int, errMsg: String) {
        showToast(errMsg)
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null) {
            mVideoView.fullWindowPlayer
        } else mVideoView
    }

    override fun onResume() {
        super.onResume()
        getCurPlay().onVideoResume()
        isPause = false
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            mVideoView.onConfigurationChanged(this, newConfig, orientationUtls)
        }
    }

    override fun onDestroy() {
        GSYVideoPlayer.releaseAllVideos()
        orientationUtls?.releaseListener()
        super.onDestroy()
    }
}
