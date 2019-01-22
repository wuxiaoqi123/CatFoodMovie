package com.welcome.catfood.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayoutManager
import com.welcome.catfood.R

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class HotKeywordsAdapter(mContext: Context, mList: ArrayList<String>, layoutId: Int) :
    CommonAdapter<String>(mContext, mList, layoutId) {

    private var mOnTagItemClick: ((tag: String) -> Unit)? = null

    fun setOnTagItemClickListener(onTagItemClickListener: (tag: String) -> Unit) {
        this.mOnTagItemClick = onTagItemClickListener
    }

    override fun bindData(holder: ViewHolder, data: String, position: Int) {
        holder.setText(R.id.tv_title, data)
        val params = holder.getView<TextView>(R.id.tv_title).layoutParams
        if (params is FlexboxLayoutManager.LayoutParams) {
            params.flexGrow = 1.0f
        }
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mOnTagItemClick?.invoke(data)
            }
        })
    }
}
