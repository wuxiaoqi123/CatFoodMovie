package com.welcome.catfood.adapter

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Suppress("UNCHECKED_CAST")
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mView: SparseArray<View>

    init {
        mView = SparseArray()
    }

    fun <T : View> getView(viewId: Int): T {
        var view: View? = mView.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mView.put(viewId, view)
        }
        return view as T
    }

    fun <T : ViewGroup> getViewGroup(viewId: Int): T {
        var view: View? = mView.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mView.put(viewId, view)
        }
        return view as T
    }

    fun setText(viewId: Int, text: CharSequence): ViewHolder {
        val view = getView<TextView>(viewId)
        view.text = "$text"
        return this
    }

    fun setHintText(viewId: Int, text: CharSequence): ViewHolder {
        val view = getView<TextView>(viewId)
        view.hint = "$text"
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): ViewHolder {
        val img = getView<ImageView>(viewId)
        img.setImageResource(resId)
        return this
    }

    fun setImagePath(viewId: Int, imageLoader: HolderImageLoader): ViewHolder {
        val img = getView<ImageView>(viewId)
        imageLoader.loadImage(img, imageLoader.path)
        return this
    }

    fun setViewVisibility(viewId: Int, visibility: Int): ViewHolder {
        getView<View>(viewId).visibility = visibility
        return this
    }

    fun setOnItemClickListener(listener: View.OnClickListener) {
        itemView.setOnClickListener(listener)
    }

    fun setOnItemLongClickListener(listener: View.OnLongClickListener) {
        itemView.setOnLongClickListener(listener)
    }

    abstract class HolderImageLoader(val path: String) {
        abstract fun loadImage(iv: ImageView, path: String)
    }
}
