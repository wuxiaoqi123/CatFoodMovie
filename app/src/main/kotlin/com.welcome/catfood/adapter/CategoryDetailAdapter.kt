package com.welcome.catfood.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.welcome.catfood.R
import com.welcome.catfood.activity.VideoDetailActivity
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.extend.durationFormat

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CategoryDetailAdapter(
    context: Context,
    dataList: ArrayList<HomeBean.Issue.Item>,
    layoutId: Int
) :
    CommonAdapter<HomeBean.Issue.Item>(context, dataList, layoutId) {

    fun clear() {
        this.mData.clear()
        notifyDataSetChanged()
    }

    fun addData(dataList: ArrayList<HomeBean.Issue.Item>) {
        this.mData.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        val itemData = data.data
        val cover = itemData?.cover?.feed ?: ""
        Glide.with(mContext)
            .load(cover)
            .apply(RequestOptions().placeholder(R.drawable.placeholder_banner))
            .transition(DrawableTransitionOptions().crossFade())
            .into(holder.getView(R.id.iv_image))
        holder.setText(R.id.tv_title, itemData?.title ?: "")
        val timeFormat = durationFormat(itemData!!.duration)
        holder.setText(R.id.tv_tag, "#${itemData?.category}/$timeFormat")
        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_image), data)
        })
    }

    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        //TODO
        activity.startActivity(Intent(activity, VideoDetailActivity::class.java))
    }
}
