package com.welcome.catfood.activity

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import com.google.android.flexbox.*
import com.welcome.catfood.R
import com.welcome.catfood.adapter.CategoryDetailAdapter
import com.welcome.catfood.adapter.HotKeywordsAdapter
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.contract.SearchContract
import com.welcome.catfood.extend.getStatusBarHeight
import com.welcome.catfood.extend.showToast
import com.welcome.catfood.net.exception.ExceptionHandler
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

    private var mHotKeywordsAdapter: HotKeywordsAdapter? = null

    private val mResultAdapter by lazy {
        CategoryDetailAdapter(
            this,
            itemList,
            R.layout.item_category_detail
        )
    }

    private var keyWords: String? = null

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private var loadingMore = false

    override fun layoutId() = R.layout.activity_search

    override fun initData(intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpEnterAnimation()
            setUpExitAnimation()
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
        initStatusBar()
        mLayoutStatusView = multipleStatusView
        mRecyclerView_result.layoutManager = LinearLayoutManager(this)
        mRecyclerView_result.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        mRecyclerView_result.adapter = mResultAdapter
    }

    private fun initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val lp = LinearLayout.LayoutParams(-1, getStatusBarHeight())
            mStatusBarView.layoutParams = lp
            mStatusBarView.requestLayout()
        }
    }

    override fun initListener() {
        tv_cancel.setOnClickListener { onBackPressed() }
        mRecyclerView_result.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                val itemCount = mRecyclerView_result.layoutManager.itemCount
                val lastVisibleItem =
                    (mRecyclerView_result.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true
                    presenterImp?.loadMoreData()
                }
            }
        })
        et_search_view.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                closeSoftKeyboard()
                keyWords = et_search_view.text.toString().trim()
                if (keyWords.isNullOrEmpty()) {
                    showToast("请输入你感兴趣的关键词")
                } else {
                    mResultAdapter.clear()
                    presenterImp?.querySearchData(keyWords!!)
                }
            }
            false
        }
    }

    override fun getPresenter(): SearchContract.Presenter = SearchPresenter(this)

    override fun startRequest() {
        presenterImp?.requestHotWordData()
    }

    override fun setHotWordData(string: ArrayList<String>) {
        showHotWordView()
        mHotKeywordsAdapter = HotKeywordsAdapter(this, string, R.layout.item_flow_text)
        val flexBoxLayoutManager = FlexboxLayoutManager(this)
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW
        flexBoxLayoutManager.alignItems = AlignItems.CENTER
        flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START
        search_hot_recyclerview.layoutManager = flexBoxLayoutManager
        search_hot_recyclerview.adapter = mHotKeywordsAdapter
        mHotKeywordsAdapter!!.setOnTagItemClickListener {
            closeSoftKeyboard()
            keyWords = it
            mResultAdapter.clear()
            presenterImp?.querySearchData(it)
        }
    }

    private fun showHotWordView() {
        layout_hot_words.visibility = View.VISIBLE
        layout_content_result.visibility = View.GONE
    }

    private fun hideHotWordView() {
        layout_hot_words.visibility = View.GONE
        layout_content_result.visibility = View.VISIBLE
    }

    override fun setSearchResult(issue: HomeBean.Issue) {
        loadingMore = false
        hideHotWordView()
        tv_search_count.visibility = View.VISIBLE
        tv_search_count.text = String.format("-「%s」搜索结果共%d个-", keyWords, issue.total)
        mResultAdapter.addData(issue.itemList)
    }

    override fun setEmptyView() {
        showToast("抱歉，没有找到相匹配的内容")
        hideHotWordView()
        tv_search_count.visibility = View.GONE
        mLayoutStatusView?.showEmpty()
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun hideLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun showErrMsg(errCode: Int, errMsg: String) {
        showToast(errMsg)
        if (errCode == ExceptionHandler.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    private fun closeSoftKeyboard() {
        closeKeyBoard(et_search_view)
    }

    private fun defaultBackPressed() {
        closeSoftKeyboard()
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
