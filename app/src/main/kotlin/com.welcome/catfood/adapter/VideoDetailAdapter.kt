package com.welcome.catfood.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.welcome.catfood.R
import com.welcome.catfood.app.CatFoodApplication
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.extend.durationFormat
import com.welcome.catfood.glide.GlideRoundTransform

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class VideoDetailAdapter(mContext: Context, data: ArrayList<HomeBean.Issue.Item>) :
    CommonAdapter<HomeBean.Issue.Item>(mContext, data, object : MultipleType<HomeBean.Issue.Item> {
        override fun getLayoutId(item: HomeBean.Issue.Item, position: Int): Int {
            return when {
                position == 0 -> R.layout.item_video_detail_info
                data[position].type == "textCard" ->
                    R.layout.item_video_text_card
                data[position].type == "videoSmallCard" ->
                    R.layout.item_video_small_card
                else ->
                    throw IllegalAccessException("api解析出错了")

            }
        }

    }) {

    fun addData(item: HomeBean.Issue.Item) {
        mData.clear()
        notifyDataSetChanged()
        mData.add(item)
        notifyItemInserted(0)
    }

    fun addData(item: ArrayList<HomeBean.Issue.Item>) {
        mData.addAll(item)
        notifyItemRangeInserted(1, item.size)
    }

    private var mOnItemClickRelatedVideo: ((item: HomeBean.Issue.Item) -> Unit)? = null

    fun setOnItemDetailClick(mItemRelatedVideo: (item: HomeBean.Issue.Item) -> Unit) {
        this.mOnItemClickRelatedVideo = mItemRelatedVideo
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when {
            position == 0 -> setVideoDetailInfo(data, holder)

            data.type == "textCard" -> {
                holder.setText(R.id.tv_text_card, data.data?.text!!)
            }
            data.type == "videoSmallCard" -> {
                with(holder) {
                    setText(R.id.tv_title, data.data?.title!!)
                    setText(
                        R.id.tv_tag,
                        "#${data.data.category} / ${durationFormat(data.data.duration)}"
                    )
                    setImagePath(
                        R.id.iv_video_small_card,
                        object : ViewHolder.HolderImageLoader(data.data.cover.detail) {
                            override fun loadImage(iv: ImageView, path: String) {
                                Glide.with(mContext)
                                    .load(path)
                                    .apply(
                                        RequestOptions().transform(GlideRoundTransform()).placeholder(
                                            R.drawable.placeholder_banner
                                        )
                                    )
                                    .into(iv)
                            }
                        })
                }
                // 判断onItemClickRelatedVideo 并使用
                holder.itemView.setOnClickListener { mOnItemClickRelatedVideo?.invoke(data) }

            }
            else -> throw IllegalAccessException("Api 解析出错了，出现其他类型")
        }
    }

    /**
     * 设置视频详情数据
     */
    private fun setVideoDetailInfo(data: HomeBean.Issue.Item, holder: ViewHolder) {
        data.data?.title?.let { holder.setText(R.id.tv_title, it) }
        //视频简介
        data.data?.description?.let { holder.setText(R.id.expandable_text, it) }
        //标签
        holder.setText(
            R.id.tv_tag,
            "#${data.data?.category} / ${durationFormat(data.data?.duration!!)}"
        )
        //喜欢
        holder.setText(R.id.tv_action_favorites, data.data?.consumption?.collectionCount.toString())
        //分享
        holder.setText(R.id.tv_action_share, data.data?.consumption?.shareCount.toString())
        //评论
        holder.setText(R.id.tv_action_reply, data.data?.consumption?.replyCount.toString())

        if (data.data?.author != null) {
            with(holder) {
                setText(R.id.tv_author_name, data.data.author.name)
                setText(R.id.tv_author_desc, data.data.author.description)
                setImagePath(
                    R.id.iv_avatar,
                    object : ViewHolder.HolderImageLoader(data.data.author.icon) {
                        override fun loadImage(iv: ImageView, path: String) {
                            //加载头像
                            Glide.with(mContext)
                                .load(path)
                                .apply(RequestOptions().placeholder(R.mipmap.default_avatar).circleCrop())
                                .into(iv)
                        }
                    })
            }
        } else {
            holder.setViewVisibility(R.id.layout_author_view, View.GONE)
        }

        with(holder) {
            getView<TextView>(R.id.tv_action_favorites).setOnClickListener {
                Toast.makeText(CatFoodApplication.context, "喜欢", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_share).setOnClickListener {
                Toast.makeText(CatFoodApplication.context, "分享", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_reply).setOnClickListener {
                Toast.makeText(CatFoodApplication.context, "评论", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
