package com.welcome.catfood.fragment

import android.os.Bundle
import android.preference.PreferenceFragment
import com.welcome.catfood.R

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/02/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class SettingFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_setting)
    }


}