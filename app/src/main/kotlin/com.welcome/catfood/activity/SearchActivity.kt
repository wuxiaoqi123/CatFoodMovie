package com.welcome.catfood.activity

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.SearchContract
import com.welcome.catfood.extend.showToast
import com.welcome.catfood.presenter.SearchPresenter
import com.welcome.catfood.utils.ViewAnimUtils
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
class SearchActivity : BaseActivity<SearchContract.Presenter>(), SearchContract.View {

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
                animateRevealShow()
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateRevealShow() {
        ViewAnimUtils.animateRevealShow(
            this, activity_search_root_rl,
            fab_circle.width / 2, R.color.backgroundColor,
            object : ViewAnimUtils.OnRevealAnimationListener {
                override fun onRevealHide() {
                }

                override fun onRevealShow() {
                    setUpView()
                }
            }
        )
    }

    private fun setUpView() {
        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        animation.duration = 300
        search_container.startAnimation(animation)
        search_container.visibility = View.VISIBLE
        //打开软键盘
        et_search_view.requestFocus()
        openKeyBoard(et_search_view)
    }

    override fun initView() {

    }

    override fun initListener() {
        tv_cancel.setOnClickListener { onBackPressed() }
    }

    override fun getPresenter(): SearchContract.Presenter = SearchPresenter(this)

    override fun startRequest() {
        presenterImp?.requestHotWordData()
    }

    override fun setHotWordData(string: ArrayList<String>) {
        Log.i("wxq", "成功->" + string[0])
    }

    override fun setSearchResult(issue: HomeBean.Issue) {
    }

    override fun setEmptyView() {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showErrMsg(errCode: Int, errMsg: String) {
        showToast(errMsg)
//        if (errorCode == ErrorStatus.NETWORK_ERROR) {
//            mLayoutStatusView?.showNoNetwork()
//        } else {
//            mLayoutStatusView?.showError()
//        }
    }

    private fun defaultBackPressed() {
        closeKeyBoard(et_search_view)
        super.onBackPressed()
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimUtils.animateRevealHide(this,
                search_container,
                fab_circle.width / 2,
                R.color.backgroundColor,
                object : ViewAnimUtils.OnRevealAnimationListener {
                    override fun onRevealHide() {
                        defaultBackPressed()
                    }

                    override fun onRevealShow() {
                    }
                })
        } else {
            defaultBackPressed()
        }
    }
}
