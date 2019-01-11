package com.welcome.catfood.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * <pre>
 *     author : 0
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class HotChildFragmentAdapter(
    fm: FragmentManager,
    private var fragmentList: ArrayList<Fragment>,
    mTitles: ArrayList<String>
) : FragmentPagerAdapter(fm) {

    private var mTitles: List<String>? = mTitles

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? {
        mTitles?.run {
            return get(position)
        }
        return ""
    }
}