package com.welcome.catfood.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxFragment
import com.welcome.catfood.app.CatFoodApplication
import com.welcome.catfood.ui.MultipleStatusView
import com.welcome.catfood.utils.LogUtil
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class BaseFragment<T : IBasePresenter> : RxFragment(), EasyPermissions.PermissionCallbacks {

    private var isViewPrepare = false

    private var hasLoadData = false

    protected var mLayoutStatusView: MultipleStatusView? = null

    protected var presenterImp: T? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        lazyLoadDataIfPrepared()
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
        presenterImp = getPresenter()
    }

    protected open fun getPresenter(): T? {
        return null
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun lazyLoad()

    override fun onDestroy() {
        super.onDestroy()
        activity?.let {
            CatFoodApplication.getRefWatcher(it)?.watch(activity)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        LogUtil.i(msg = "权限获取成功->$perms")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        val sb = StringBuilder()
        for (str in perms) {
            sb.append("${str}\n")
        }
        sb.replace(sb.length - 2, sb.length, "")
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this)
                .setRationale("此功能需要${sb}权限，否则无法正常使用")
                .setPositiveButton("联同意")
                .setNegativeButton("托下去砍了")
                .build()
                .show()
        }
    }

    open fun <T> bindToLife(): LifecycleTransformer<T> {
        return bindUntilEvent(FragmentEvent.DESTROY)
    }
}
