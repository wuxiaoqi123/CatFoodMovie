package com.welcome.catfood.app

import android.app.Application
import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.welcome.catfood.BuildConfig
import kotlin.properties.Delegates

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CatFoodApplication : Application() {

    private var refWatcher: RefWatcher? = null

    companion object {
        const val TAG = "CatFoodMovie"

        var context: Context by Delegates.notNull()
            private set

        fun getRefWatcher(context: Context): RefWatcher? {
            val catFoodApplication = context.applicationContext as CatFoodApplication
            return catFoodApplication.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initConfig()
        com.welcome.catfood.manager.DisplayManager
//        refWatcher = setupLeakCanary()
    }

    private fun initConfig() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)
            .methodCount(0)
            .methodOffset(7)
            .tag(TAG)
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    private fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this))
            RefWatcher.DISABLED
        else LeakCanary.install(this)
    }
}
