package com.welcome.catfood.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import com.welcome.catfood.R
import com.welcome.catfood.extend.dp2px

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class RealtimeBlurView : View {

    private var mDownsampleFactor: Float = 4f
    private var mOverlayColor: Int = 0
    private var mBlurRadius: Float = 10f

    private var mDirty: Boolean = false
    private var mBitmapToBlur: Bitmap? = null
    private var mBlurredBitmap: Bitmap? = null
    private var mBlurringCanvas: Canvas? = null
    private var mRenderScript: RenderScript? = null
    private var mBlurScript: ScriptIntrinsicBlur? = null
    private var mBlurInput: Allocation? = null
    private var mBlurOutput: Allocation? = null
    private var mIsRendering: Boolean = false
    private val mRectSrc: Rect = Rect()
    private val mRectDst: Rect = Rect()
    private var mDecorView: View? = null
    private var mDifferentRoot: Boolean = false

    companion object {
        private var RENDERING_COUNT = 0

        init {
            try {
                RealtimeBlurView::class.java.getClassLoader().loadClass("android.support.v8.renderscript.RenderScript")
            } catch (e: ClassNotFoundException) {
                throw  RuntimeException("RenderScript support not enabled. Add \"android { defaultConfig { renderscriptSupportModeEnabled true }}\" in your build.gradle")
            }
        }
    }

    constructor(context: Context?) : super(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = context!!.obtainStyledAttributes(attrs, R.styleable.RealtimeBlurView)
        mBlurRadius = a.getDimension(R.styleable.RealtimeBlurView_realtimeBlurRadius, dp2px(10f))
        mDownsampleFactor = a.getFloat(R.styleable.RealtimeBlurView_realtimeDownsampleFactor, 4f)
        mOverlayColor = a.getColor(R.styleable.RealtimeBlurView_realtimeOverlayColor, Color.parseColor("0xAAFFFFFF"))
        a.recycle()
    }

    fun setBlurRadius(radius: Float) {
        if (mBlurRadius != radius) {
            mBlurRadius = radius
            mDirty = true
            invalidate()
        }
    }

    fun setDownsampleFactor(factor: Float) {
        if (factor <= 0) {
            throw IllegalArgumentException("Downsample factor must be greater than 0.")
        }
        if (mDownsampleFactor != factor) {
            mDownsampleFactor = factor
            mDirty = true
            releaseBitmap()
            invalidate()
        }
    }

    fun setOverlayColor(color: Int) {
        if (mOverlayColor != color) {
            mOverlayColor = color
            invalidate()
        }
    }

    private fun releaseBitmap() {
        mBlurInput?.destroy()
        mBlurInput = null
        mBlurOutput?.destroy()
        mBlurOutput = null
        mBitmapToBlur?.recycle()
        mBitmapToBlur = null
        mBlurredBitmap?.recycle()
        mBlurredBitmap = null
    }

    private fun releaseScript() {
        mRenderScript?.destroy()
        mRenderScript = null
        mBlurScript?.destroy()
        mBlurScript = null
    }

    protected fun release() {
        releaseBitmap()
        releaseScript()
    }

    protected fun prepare(): Boolean {
        if (mBlurRadius.toInt() == 0) {
            release()
            return false
        }

        var downsampleFactor = mDownsampleFactor
        if (mDirty || mRenderScript == null) {
            if (mRenderScript == null) {
                try {
                    mRenderScript = RenderScript.create(context)
                    mBlurScript = ScriptIntrinsicBlur.create(mRenderScript, Element.U8_4(mRenderScript))
                } catch (e: Exception) {
                    e.printStackTrace()
                    releaseScript()
                    return false
                }
            }
        }

        mDirty = false
        var radius = mBlurRadius / downsampleFactor
        if (radius > 25) {
            downsampleFactor = downsampleFactor * radius / 25
            radius = 25f
        }
        mBlurScript?.setRadius(radius)

        val width = getWidth()
        val height = getHeight()

        var scaledWidth = Math.max(1, (width / downsampleFactor).toInt())
        var scaledHeight = Math.max(1, (height / downsampleFactor).toInt())

        if (mBlurringCanvas == null || mBlurredBitmap == null
            || mBlurredBitmap?.width != scaledWidth.toInt()
            || mBlurredBitmap?.height != scaledHeight.toInt()
        ) {
            releaseBitmap()

            var r = false
            try {
                mBitmapToBlur = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
                if (mBitmapToBlur == null) return false
                mBlurringCanvas = Canvas(mBitmapToBlur)
                mBlurInput = Allocation.createFromBitmap(
                    mRenderScript, mBitmapToBlur,
                    Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT
                )
                mBlurOutput = Allocation.createTyped(mRenderScript, mBlurInput?.getType())

                mBlurredBitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
                if (mBlurredBitmap == null) {
                    return false
                }

                r = true
            } catch (e: Exception) {

            } finally {
                if (!r) {
                    releaseBitmap()
                    return false
                }
            }
        }
        return true
    }

    protected fun blur(bitmapToBlur: Bitmap?, blurredBitmap: Bitmap?) {
        mBlurInput?.copyFrom(bitmapToBlur)
        mBlurScript?.setInput(mBlurInput)
        mBlurScript?.forEach(mBlurOutput)
        mBlurOutput?.copyTo(blurredBitmap)
    }

    private val preDrawListener = object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            val locations = IntArray(2)
            var oldBmp = mBlurredBitmap
            var decor = mDecorView
            if (decor != null && isShown && prepare()) {
                var redrawBitmap = mBlurredBitmap != oldBmp
                oldBmp = null
                decor.getLocationOnScreen(locations)
                var x = -locations[0]
                var y = -locations[1]

                getLocationOnScreen(locations)
                x += locations[0]
                y += locations[1]

                mBitmapToBlur?.eraseColor(mOverlayColor and 0xffffff)

                var rc = mBlurringCanvas!!.save()
                mIsRendering = true
                RENDERING_COUNT++
                try {
                    mBlurringCanvas?.scale(
                        1f * mBitmapToBlur!!.getWidth() / width,
                        1f * mBitmapToBlur!!.getHeight() / height
                    )
                    mBlurringCanvas!!.translate((-x).toFloat(), (-y).toFloat())
                    if (decor.background != null) {
                        decor.background.draw(mBlurringCanvas)
                    }
                    decor.draw(mBlurringCanvas)
                } catch (e: Exception) {
                } finally {
                    mIsRendering = false
                    RENDERING_COUNT--
                    mBlurringCanvas?.restoreToCount(rc)
                }

                blur(mBitmapToBlur, mBlurredBitmap)

                if (redrawBitmap || mDifferentRoot) {
                    invalidate()
                }
            }
            return true
        }
    }

    protected fun getActivityDecorView(): View? {
        var ctx = context
        for (i in 0..4) {
            if (ctx != null && !(ctx is Activity) && (ctx is ContextWrapper)) {
                ctx = ctx.baseContext
            }
        }
        if (ctx is Activity) {
            return ctx.window.decorView
        }
        return null
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mDecorView = getActivityDecorView()
        if (mDecorView != null) {
            mDecorView?.viewTreeObserver?.addOnPreDrawListener(preDrawListener)
            mDifferentRoot = mDecorView?.rootView != rootView
            if (mDifferentRoot) {
                mDecorView?.postInvalidate()
            }
        } else {
            mDifferentRoot = false
        }
    }

    override fun onDetachedFromWindow() {
        mDecorView?.viewTreeObserver?.removeOnPreDrawListener(preDrawListener)
        release()
        super.onDetachedFromWindow()
    }

    override fun draw(canvas: Canvas?) {
        if (mIsRendering) {
            throw RuntimeException()
        } else if (RENDERING_COUNT > 0) {

        } else {
            super.draw(canvas)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBlurredBitmap(canvas, mBlurredBitmap, mOverlayColor)
    }

    protected fun drawBlurredBitmap(canvas: Canvas, blurredBitmap: Bitmap?, overlayColor: Int) {
        if (blurredBitmap != null) {
            mRectSrc.right = blurredBitmap.width
            mRectSrc.bottom = blurredBitmap.height
            mRectDst.right = width
            mRectDst.bottom = height
            canvas.drawBitmap(blurredBitmap, mRectSrc, mRectDst, null)
        }
        canvas.drawColor(overlayColor)
    }


}