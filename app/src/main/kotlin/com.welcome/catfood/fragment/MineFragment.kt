package com.welcome.catfood.fragment

import android.content.Intent
import com.welcome.catfood.R
import com.welcome.catfood.activity.AboutActivity
import com.welcome.catfood.activity.PersonalHomepageActivity
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.base.IBasePresenter
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/15
 *     desc   : 我的
 *     version: 1.0
 * </pre>
 */
class MineFragment : BaseFragment<IBasePresenter>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        iv_about.setOnClickListener {
            activity?.let { AboutActivity.start(it) }
        }
        tv_view_homepage.setOnClickListener {
            startActivity(Intent(activity, PersonalHomepageActivity::class.java))
        }
    }

    override fun lazyLoad() {
    }
}
