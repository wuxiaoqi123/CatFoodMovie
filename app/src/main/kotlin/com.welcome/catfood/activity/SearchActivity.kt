package com.welcome.catfood.activity

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.animation.AnimationUtils
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.base.IBasePresenter
import kotlinx.android.synthetic.main.activity_search.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class SearchActivity : BaseActivity<IBasePresenter>() {

    override fun layoutId() = R.layout.activity_search

    override fun initData(intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpEnterAnimation();
            setUpExitAnimation();
        } else {
            val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
            animation.duration = 300
            search_container.startAnimation(animation)
            search_container.visibility = View.VISIBLE
            openKeyBoard(et_search_view)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpEnterAnimation() {
        val transition = TransitionInflater.from(this)
            .inflateTransition(R.transition.arc_motion)
        window.sharedElementEnterTransition = transition
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
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
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpExitAnimation() {
        val fade = Fade()
        window.returnTransition = fade
        fade.duration = 300
    }

    override fun initView() {
    }

    override fun startRequest() {
    }
}
