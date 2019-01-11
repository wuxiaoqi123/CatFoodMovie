package com.welcome.catfood.fragment

import android.support.v4.app.Fragment
import com.welcome.catfood.R
import com.welcome.catfood.adapter.HotChildFragmentAdapter
import com.welcome.catfood.base.BaseFragment
import com.welcome.catfood.base.IBasePresenter
import com.welcome.catfood.utils.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_find.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/15
 *     desc   : 发现
 *     version: 1.0
 * </pre>
 */
class FindFragment : BaseFragment<IBasePresenter>() {

    private val tabTitleList = ArrayList<String>()
    private val tabFragmentList = ArrayList<Fragment>()

    override fun getLayoutId(): Int = R.layout.fragment_find

    override fun initView() {
        tabTitleList.add("关注")
        tabTitleList.add("分类")
        tabFragmentList.add(FollowFragment.getInstance("关注"))
        tabFragmentList.add(CategoryFragment.getInstance("分类"))
        mViewPager.adapter =
                HotChildFragmentAdapter(childFragmentManager, tabFragmentList, tabTitleList)
        mTabLayout.setupWithViewPager(mViewPager)
        TabLayoutHelper.setUpIndicorWidth(mTabLayout)
    }

    override fun lazyLoad() {
    }
}
