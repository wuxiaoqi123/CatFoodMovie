package com.welcome.catfood.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.welcome.catfood.R

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MultipleStatusView : RelativeLayout {

    companion object {
        const val TAG = "MultipStatusView"

        val DEFAULT_LAYOUT_PARAMS = RelativeLayout.LayoutParams(-1, -1)

        const val STATUS_CONTENT = 0
        const val STATUS_LOADING = 1
        const val STATUS_EMPTY = 2
        const val STATUS_ERROR = 3
        const val STATUS_NO_NETWORK = 4

        const val NULL_RESOURCE_ID = -1
    }

    private var mEmptyView: View? = null
    private var mErrorView: View? = null
    private var mLoadingView: View? = null
    private var mNoNetworkView: View? = null
    private var mContentView: View? = null

    private var mEmptyViewResId: Int? = null
    private var mErrorViewResId: Int? = null
    private var mLoadingViewResId: Int? = null
    private var mNoNetworkViewResId: Int? = null
    private var mContentViewResId: Int? = null

    var mViewStatus: Int = STATUS_CONTENT
        get() = field

    private var mInflater: LayoutInflater? = null

    var mOnRetryClickListener: OnClickListener? = null

    private val mOtherIds = ArrayList<Int>()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val a =
            context.obtainStyledAttributes(attrs, R.styleable.MultipleStatusView, defStyleAttr, 0)
        mEmptyViewResId = a.getResourceId(
            R.styleable.MultipleStatusView_emptyView,
            R.layout.multip_empty_view
        )
        mErrorViewResId = a.getResourceId(
            R.styleable.MultipleStatusView_errorView,
            R.layout.multip_error_view
        )
        mLoadingViewResId = a.getResourceId(
            R.styleable.MultipleStatusView_loadingView,
            R.layout.multip_loading_view
        )
        mNoNetworkViewResId =
                a.getResourceId(
                    R.styleable.MultipleStatusView_noNetworkView,
                    R.layout.multip_no_network_view
                )
        mContentViewResId =
                a.getResourceId(R.styleable.MultipleStatusView_contentView, NULL_RESOURCE_ID)
        a.recycle()
        mInflater = LayoutInflater.from(context)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        showContent()
    }

//    override fun onDetachedFromWindow() {
//        super.onDetachedFromWindow()
//        Log.i("wxq", "执行onDetachedFromWindow")
//        clear(mEmptyView, mLoadingView, mErrorView, mNoNetworkView)
//        mOtherIds.clear()
//        mOnRetryClickListener = null
//        mInflater = null
//    }

    fun showEmpty(
        layoutId: Int? = mEmptyViewResId,
        lp: ViewGroup.LayoutParams = DEFAULT_LAYOUT_PARAMS
    ) {
        showEmpty(inflateView(layoutId!!), lp)
    }

    fun showEmpty(view: View?, lp: ViewGroup.LayoutParams = DEFAULT_LAYOUT_PARAMS) {
        view?.let {
            mViewStatus = STATUS_EMPTY
            if (mEmptyView == null) {
                mEmptyView = it
                val emptyRetryView: View = mEmptyView!!.findViewById(R.id.empty_view)
                emptyRetryView.setOnClickListener(mOnRetryClickListener)
                mOtherIds.add(mEmptyView!!.id)
                addView(mEmptyView, 0, lp)
            }
            showViewById(mEmptyView!!.id)
        } ?: throw NullPointerException("Empty view is null")
    }

    fun showError(
        layoutId: Int? = mErrorViewResId,
        lp: ViewGroup.LayoutParams = DEFAULT_LAYOUT_PARAMS
    ) {
        showError(inflateView(layoutId!!), lp)
    }

    fun showError(view: View?, lp: ViewGroup.LayoutParams = DEFAULT_LAYOUT_PARAMS) {
        view?.let {
            mViewStatus = STATUS_ERROR
            if (mErrorView == null) {
                mErrorView = it
                val errorRetryView: View = mErrorView!!.findViewById(R.id.error_view)
                errorRetryView.setOnClickListener(mOnRetryClickListener)
                mOtherIds.add(mErrorView!!.id)
                addView(mErrorView, 0, lp)
            }
            showViewById(mErrorView!!.id)
        } ?: throw NullPointerException("Error view is null")
    }

    fun showLoading(
        layoutId: Int? = mLoadingViewResId,
        lp: ViewGroup.LayoutParams = DEFAULT_LAYOUT_PARAMS
    ) {
        showLoading(inflateView(layoutId!!), lp)
    }

    fun showLoading(view: View?, lp: ViewGroup.LayoutParams = DEFAULT_LAYOUT_PARAMS) {
        view?.let {
            mViewStatus = STATUS_LOADING
            if (mLoadingView == null) {
                mLoadingView = it
                mOtherIds.add(mLoadingView!!.id)
                addView(mLoadingView, 0, lp)
            }
            showViewById(mLoadingView!!.id)
        } ?: throw NullPointerException("Loading view is null!")
    }

    fun showViewById(id: Int) {
        for (i in 0..childCount - 1) {
            if (getChildAt(i).id == id) {
                getChildAt(i).visibility = View.VISIBLE
            } else {
                getChildAt(i).visibility = View.GONE
            }
        }
    }

    fun showNoNetwork(
        layoutId: Int? = mNoNetworkViewResId,
        lp: ViewGroup.LayoutParams = DEFAULT_LAYOUT_PARAMS
    ) {
        showNoNetwork(inflateView(layoutId!!), lp)
    }

    fun showNoNetwork(view: View?, lp: ViewGroup.LayoutParams = DEFAULT_LAYOUT_PARAMS) {
        view?.let {
            mViewStatus = STATUS_NO_NETWORK
            if (mNoNetworkView == null) {
                mNoNetworkView = it
                val noNetworkRetryView: View = mNoNetworkView!!.findViewById(R.id.no_network_view)
                noNetworkRetryView.setOnClickListener(mOnRetryClickListener)
                mOtherIds.add(mNoNetworkView!!.id)
                addView(mNoNetworkView, 0, lp)
            }
            showViewById(mNoNetworkView!!.id)
        } ?: throw NullPointerException("No network view is null!")
    }

    fun showContent() {
        mViewStatus = STATUS_CONTENT
        if (mContentView == null && mContentViewResId != NULL_RESOURCE_ID) {
            mContentView = mInflater?.inflate(mContentViewResId!!, null)
            addView(mContentView, 0, DEFAULT_LAYOUT_PARAMS)
        }
        showContentView()
    }

    private fun showContentView() {
        for (i in 0..childCount) {
            val view: View? = getChildAt(i)
            view?.visibility = if (mOtherIds.contains(view?.id)) View.GONE else View.VISIBLE
        }
    }

    private fun inflateView(layoutId: Int): View? {
        return mInflater?.inflate(layoutId, null)
    }

    private fun clear(vararg arg: View?) {
        arg.let {
            for (a in arg) {
                a?.run {
                    removeView(this)
                }
            }
        }
    }
}
