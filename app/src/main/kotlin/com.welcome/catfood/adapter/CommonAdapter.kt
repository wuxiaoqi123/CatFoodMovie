package com.welcome.catfood.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/11/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class CommonAdapter<T>(var context: Context, var mData: ArrayList<T>, private var mLayoutId: Int) :
    RecyclerView.Adapter<ViewHolder>() {

    protected var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mTypeSupport: MultipleType<T>? = null

    private var mItemClickListener: OnItemClickListener? = null
        set(value) {
            field = value
        }

    private var mItemLongClickListener: OnItemLongClickListener? = null
        set(value) {
            field = value
        }

    constructor(context: Context, data: ArrayList<T>, typeSupport: MultipleType<T>) : this(context, data, -1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mTypeSupport != null) {
            mLayoutId = viewType
        }
        val view = mInflater.inflate(mLayoutId, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return mTypeSupport?.getLayoutId(mData[position], position) ?: super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindData(holder, mData[position], position)
        mItemClickListener?.let {
            holder.itemView.setOnClickListener { mItemClickListener!!.onItemClick(mData[position], position) }
        }
        mItemLongClickListener?.let {
            holder.itemView.setOnLongClickListener {
                mItemLongClickListener!!.onItemLongClick(
                    mData[position],
                    position
                )
            }
        }
    }

    protected abstract fun bindData(holder: ViewHolder, data: T, position: Int)

    override fun getItemCount(): Int {
        return mData.size
    }
}