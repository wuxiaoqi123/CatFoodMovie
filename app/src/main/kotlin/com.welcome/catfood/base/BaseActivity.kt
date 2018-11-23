package com.welcome.catfood.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.welcome.catfood.app.CatFoodApplication
import com.welcome.catfood.ui.MultipleStatusView
import com.welcome.catfood.utils.LogUtil
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class BaseActivity<P : IBasePresenter> : RxAppCompatActivity(),
    EasyPermissions.PermissionCallbacks {

    protected var mLayoutStatusView: MultipleStatusView? = null

    protected var presenterImp: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initData(intent);
        initView()
        initListener()
        presenterImp = getPresenterImp()
        startRequest()
    }

    protected open fun initData(intent: Intent?) {

    }

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun initView()

    protected open fun initListener() {
        mLayoutStatusView?.setOnClickListener { startRequest() }
    }

    protected open fun <P> getPresenterImp(): P? {
        return null
    }

    abstract fun startRequest()

    fun openKeyBoard(mInput: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mInput, 0)
    }

    fun closeKeyBoard(mInput: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mInput.windowToken, 0)
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
        return bindUntilEvent(ActivityEvent.DESTROY)
    }

    override fun onDestroy() {
        super.onDestroy()
        CatFoodApplication.getRefWatcher(this)!!.watch(this)
    }
}
