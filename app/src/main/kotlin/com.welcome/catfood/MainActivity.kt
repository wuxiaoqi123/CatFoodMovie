package com.welcome.catfood

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.fragment.HomeFragment
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
            .setIndicator(buildIndicator("首页", R.drawable.ic_home_normal))
        val hotTabSpec = tabhost.newTabSpec("hot")
            .setIndicator(buildIndicator("热门", R.drawable.ic_hot_normal))
        val findTabSpec = tabhost.newTabSpec("find")
            .setIndicator(buildIndicator("发现", R.drawable.ic_hot_normal))
        val mineTabSpec = tabhost.newTabSpec("mine")
            .setIndicator(buildIndicator("我的", R.drawable.ic_hot_normal))
        tabhost.clearAllTabs()
        tabhost.addTab(homeTabSpec, HomeFragment::class.java, null)
        tabhost.addTab(hotTabSpec, HomeFragment::class.java, null)
        tabhost.addTab(findTabSpec, HomeFragment::class.java, null)
        tabhost.addTab(mineTabSpec, HomeFragment::class.java, null)
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
}
