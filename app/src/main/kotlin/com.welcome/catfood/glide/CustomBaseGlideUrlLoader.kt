package com.welcome.catfood.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import java.io.InputStream
import java.util.regex.Pattern

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CustomBaseGlideUrlLoader(concreateLoader: ModelLoader<GlideUrl, InputStream>, modelCache: ModelCache<String, GlideUrl>) :
        BaseGlideUrlLoader<String>(concreateLoader, modelCache) {

    companion object {
        private val urlCache = ModelCache<String, GlideUrl>(150)

        private val PATTERN = Pattern.compile("__w-((?:-?\\d+)+)__")
    }

    override fun getUrl(model: String, width: Int, height: Int, options: Options?): String {
        val m = PATTERN.matcher(model)
        var bestBucket: Int
        if (m.find()) {
            val found = m.group(1).split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (bucketStr in found) {
                bestBucket = Integer.parseInt(bucketStr)
                if (bestBucket >= width) {
                    // the best bucket is the first immediately bigger than the requested width
                    break
                }
            }

        }
        return model
    }

    override fun handles(model: String): Boolean = true


    class Factory : ModelLoaderFactory<String, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
            return CustomBaseGlideUrlLoader(multiFactory.build(GlideUrl::class.java, InputStream::class.java), urlCache)
        }

        override fun teardown() {
        }

    }
}
