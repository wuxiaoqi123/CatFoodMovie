package com.welcome.catfood.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.support.v4.widget.NestedScrollView
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.extend.getStatusBarHeight
import com.welcome.catfood.utils.AppUtils
import kotlinx.android.synthetic.main.activity_personal_homepage.*
import java.util.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/02/13
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class PersonalHomepageActivity : BaseActivity<IBasePresenter>() {

    private var mOffset = 0
    private var mScrollY = 0

    override fun layoutId(): Int = R.layout.activity_personal_homepage

    private fun initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val lp = LinearLayout.LayoutParams(-1, getStatusBarHeight())
            mStatusBarView.layoutParams = lp
            mStatusBarView.requestLayout()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        initStatusBar()
        toolbar.setBackgroundColor(0)
        buttonBarLayout.alpha = 0f
        toolbar.setNavigationOnClickListener { finish() }
        refreshLayout.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onHeaderPulling(
                header: RefreshHeader?,
                percent: Float,
                offset: Int,
                headerHeight: Int,
                extendHeight: Int
            ) {
                mOffset = offset / 2
                parallax.translationY = (mOffset - mScrollY).toFloat()
                toolbar.alpha = 1 - Math.min(percent, 1f)
                mStatusBarView.alpha = toolbar.alpha
            }

            override fun onHeaderReleasing(
                header: RefreshHeader?,
                percent: Float,
                offset: Int,
                footerHeight: Int,
                extendHeight: Int
            ) {
                mOffset = offset / 2
                parallax.translationY = (mOffset - mScrollY).toFloat()
                toolbar.alpha = 1 - Math.min(percent, 1f)
                mStatusBarView.alpha = toolbar.alpha
            }
        })
        scrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            private var lastScrollY = 0
            private val h = AppUtils.dp2px(170f).toInt()
            private val color = Color.parseColor("#ffffff") and 0x00ffffff

            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                var tScrollY = scrollY
                if (lastScrollY < h) {
                    tScrollY = Math.min(h, tScrollY)
                    mScrollY = if (tScrollY > h) h else tScrollY
                    buttonBarLayout.alpha = 1f * mScrollY / h
                    mStatusBarView.alpha = buttonBarLayout.alpha
                    toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                    parallax.translationY = (mOffset - mScrollY).toFloat()
                }
                lastScrollY = tScrollY
            }
        })

        mWebView.settings.javaScriptEnabled = true
        mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                refreshLayout.finishRefresh()
                view?.loadUrl(
                    String.format(
                        Locale.CHINA,
                        "javascript:document.body.style.paddingTop='%fpx'; void 0",
                        DensityUtil.px2dp(mWebView.paddingTop.toFloat())
                    )
                )
            }
        }
        refreshLayout.setOnRefreshListener { mWebView.loadUrl("https://www.baidu.com/") }
        refreshLayout.autoRefresh()
    }

    override fun startRequest() {
    }
}
