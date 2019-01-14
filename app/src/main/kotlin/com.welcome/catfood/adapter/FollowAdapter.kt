package com.welcome.catfood.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.welcome.catfood.R
import com.welcome.catfood.bean.HomeBean

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class FollowAdapter(context: Context, dataList: ArrayList<HomeBean.Issue.Item>) :
    CommonAdapter<HomeBean.Issue.Item>(
        context,
        dataList,
        object : MultipleType<HomeBean.Issue.Item> {
            override fun getLayoutId(item: HomeBean.Issue.Item, position: Int): Int {
                return when {
                    item.type == "videoCollectionWithBrief" ->
                        R.layout.item_follow
                    else ->
                        throw IllegalAccessException("解析异常")
                }
            }
        }) {

    fun addData(dataList: ArrayList<HomeBean.Issue.Item>) {
        this.mData.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when (data.type) {
            "videoCollectionWithBrief" -> setAuthorInfo(data, holder)
        }
    }

    private fun setAuthorInfo(data: HomeBean.Issue.Item, holder: ViewHolder) {
        val headImg = data.data?.header
        holder.setImagePath(R.id.iv_avatar, object : ViewHolder.HolderImageLoader(headImg?.icon!!) {
            override fun loadImage(iv: ImageView, path: String) {
                Glide.with(mContext)
                    .load(path)
                    .apply(RequestOptions().placeholder(R.mipmap.default_avatar).circleCrop())
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(iv)
            }
        })
        holder.setText(R.id.tv_title, headImg.title)
        holder.setText(R.id.tv_desc, headImg.description)
        val recyclerView = holder.getView<RecyclerView>(R.id.fl_recyclerView)
        recyclerView.layoutManager =
                LinearLayoutManager(mContext as Activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = FollowHorizontalAdapter(
            mContext,
            data.data.itemList,
            R.layout.item_follow_horizontal
        )
    }
}
