package com.welcome.catfood.activity

import android.animation.Animator
import android.content.Intent
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.welcome.catfood.MainActivity
import com.welcome.catfood.R
import com.welcome.catfood.base.BaseActivity
import com.welcome.catfood.base.IBasePresenter
import kotlinx.android.synthetic.main.activity_splash.*
import android.R.attr.resource
import java.lang.reflect.AccessibleObject.setAccessible



/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/09
 *     desc   : 启动页
 *     version: 1.0
 * </pre>
 */
class SplashActivity : BaseActivity<IBasePresenter>() {

    override fun layoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        Glide.with(this).load(R.drawable.picture_splash).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean = false

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                val gifDrawable = resource as GifDrawable
                try {
                    val gifStateField = GifDrawable::class.java.getDeclaredField("state")
                    gifStateField.isAccessible = true
                    val gifStateClass = Class.forName("com.bumptech.glide.load.resource.gif.GifDrawable\$GifState")
                    val gifFrameLoaderField = gifStateClass.getDeclaredField("frameLoader")
                    gifFrameLoaderField.isAccessible = true
                    val gifFrameLoaderClass = Class.forName("com.bumptech.glide.load.resource.gif.GifFrameLoader")
                    val gifDecoderField = gifFrameLoaderClass.getDeclaredField("gifDecoder")
                    gifDecoderField.isAccessible = true
                    val gifDecoderClass = Class.forName("com.bumptech.glide.gifdecoder.GifDecoder")
                    val gifDecoder = gifDecoderField.get(gifFrameLoaderField.get(gifStateField.get(resource)))
                    val getDelayMethod = gifDecoderClass.getDeclaredMethod("getDelay", Int::class.javaPrimitiveType)
                    getDelayMethod.isAccessible = true
                    //设置只播放一次
                    gifDrawable.setLoopCount(1)
                    //获得总帧数
                    val count = gifDrawable.getFrameCount()
                    var delay = 0
                    for (i in 0 until count) {
                        //计算每一帧所需要的时间进行累加
                        delay += getDelayMethod.invoke(gifDecoder, i) as Int
                    }
                    splash_img.postDelayed({
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, delay.toLong())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return false
            }

        }).into(splash_img)
        splash_img.animate().alpha(1f).setDuration(2000).start()
        splash_name_tv.animate().alpha(1f).setDuration(1000).start()
        splash_version_name_tv.animate().alpha(1f).setDuration(4000).start()
    }

    override fun startRequest() {

    }

}