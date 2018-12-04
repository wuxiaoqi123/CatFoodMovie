package com.welcome.catfood.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.extend.showToast
import com.welcome.catfood.fragment.FindFragment
import com.welcome.catfood.fragment.HomeFragment
import com.welcome.catfood.fragment.HotFragment
import com.welcome.catfood.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<IBasePresenter>() {

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        initTabs()
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
    }

    /**
     * 给Tab设置图标和文字
     */
    private fun buildIndicator(title: String, icon: Int): View {
        @SuppressLint("InflateParams") val view = LayoutInflater.from(this).inflate(R.layout.tab_main_item, null)
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
        if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
            super.onBackPressed()
        } else {
            mExitTime = System.currentTimeMillis()
            showToast("再按一次关闭App")
        }
    }
}
