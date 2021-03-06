package com.welcome.catfood.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.extend.getStatusBarHeight
import com.welcome.catfood.extend.showToastCenter
import com.welcome.catfood.fragment.FindFragment
import com.welcome.catfood.fragment.HomeFragment
import com.welcome.catfood.fragment.HotFragment
import com.welcome.catfood.fragment.MineFragment
import com.welcome.catfood.ui.AboutDialog
import com.welcome.catfood.ui.ProjectAddressDialog
import com.welcome.catfood.ui.ScanDownLoadDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_main_content.*

class MainActivity : BaseActivity<IBasePresenter>() {

    private val projectAddressDialog: ProjectAddressDialog by lazy { ProjectAddressDialog(this) }
    private val scanDownLoadDialog: ScanDownLoadDialog by lazy { ScanDownLoadDialog(this) }
    private val aboutDialog: AboutDialog by lazy { AboutDialog(this) }

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView() {
        initStatusBar()
        initTabs()
        initLeftMenu()
    }

    private fun initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val lp = LinearLayout.LayoutParams(-1, getStatusBarHeight())
            mStatusBarView.layoutParams = lp
            mStatusBarView.requestLayout()
        }
    }

    private fun initTabs() {
        tabhost.setup(this, supportFragmentManager, R.id.main_content_fl)
        val homeTabSpec = tabhost.newTabSpec("home")
            .setIndicator(buildIndicator("首页", R.drawable.selector_home_tab))
        val hotTabSpec = tabhost.newTabSpec("hot")
            .setIndicator(buildIndicator("热门", R.drawable.selector_hot_tab))
        val findTabSpec = tabhost.newTabSpec("find")
            .setIndicator(buildIndicator("发现", R.drawable.selector_find_tab))
        val mineTabSpec = tabhost.newTabSpec("mine")
            .setIndicator(buildIndicator("我的", R.drawable.selector_mine_tab))
        tabhost.clearAllTabs()
        tabhost.addTab(homeTabSpec, HomeFragment::class.java, null)
        tabhost.addTab(hotTabSpec, HotFragment::class.java, null)
        tabhost.addTab(findTabSpec, FindFragment::class.java, null)
        tabhost.addTab(mineTabSpec, MineFragment::class.java, null)
        tabhost.tabWidget.setDividerDrawable(android.R.color.transparent)
        tabhost.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun initLeftMenu() {
        activity_main_nav_view.itemIconTintList = null
        activity_main_nav_view.setNavigationItemSelectedListener {
            drawerlayout.closeDrawer(Gravity.START)
            when (it.itemId) {
                R.id.project -> showProjectAddressDialog()
                R.id.down -> showScanDownLoadDialog()
                R.id.share -> share()
                R.id.about -> about()
                R.id.exit -> exit()
            }
            true
        }
    }

    private fun showProjectAddressDialog() {
        projectAddressDialog.show()
    }

    private fun showScanDownLoadDialog() {
        scanDownLoadDialog.show()
    }

    private fun share() {
        val intent = Intent().setAction(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_url))
        startActivity(Intent.createChooser(intent, "分享"))
    }

    private fun about() {
        aboutDialog.show()
    }

    private fun exit() {
        finish()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    /**
     * 给Tab设置图标和文字
     */
    private fun buildIndicator(title: String, icon: Int): View {
        @SuppressLint("InflateParams") val view =
            LayoutInflater.from(this).inflate(R.layout.tab_main_item, null)
        val img: ImageView = view.findViewById(R.id.tab_icon_img)
        val text: TextView = view.findViewById(R.id.tab_icon_tv)
        img.setBackgroundResource(icon)
        text.setText(title)
        return view
    }

    override fun startRequest() {
    }

    private var mExitTime: Long = 0

    override fun onBackPressed() {
        if (drawerlayout.isDrawerOpen(Gravity.START)) {
            drawerlayout.closeDrawer(Gravity.START)
            return
        }
        if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
            super.onBackPressed()
        } else {
            mExitTime = System.currentTimeMillis()
            showToastCenter("再按一次关闭App")
        }
//        moveTaskToBack(true)
    }
}
