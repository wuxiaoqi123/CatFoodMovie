package com.welcome.catfood.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.welcome.catfood.R
import com.welcome.catfood.activity.VideoDetailActivity
import com.welcome.catfood.bean.HomeBean
import com.welcome.catfood.extend.durationFormat
import io.reactivex.Observable
import java.util.*

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/12/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class HomeAdapter(context: Context, data: ArrayList<HomeBean.Issue.Item>) :
    CommonAdapter<HomeBean.Issue.Item>(context, data, -1) {

    companion object {
        private const val ITEM_TYPE_BANNER = 1
        private const val ITEM_TYPE_TEXT_HEADER = 2
        private const val ITEM_TYPE_CONTENT = 3
    }

    var bannerItemSize = 0

    fun setBannerSize(count: Int) {
        bannerItemSize = count
    }

    fun addItemData(itemList: ArrayList<HomeBean.Issue.Item>) {
        this.mData.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> ITEM_TYPE_BANNER
            mData[position + bannerItemSize - 1].type == "textHeader" ->
                ITEM_TYPE_TEXT_HEADER
            else -> ITEM_TYPE_CONTENT
        }
    }

    override fun getItemCount(): Int {
        return when {
            mData.size > bannerItemSize -> mData.size - bannerItemSize + 1
            mData.isEmpty() -> 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BANNER -> ViewHolder(inflaterView(R.layout.item_home_banner, parent))
            ITEM_TYPE_TEXT_HEADER -> ViewHolder(inflaterView(R.layout.item_home_header, parent))
            else -> ViewHolder(inflaterView(R.layout.item_home_content, parent))
        }
    }

    private fun inflaterView(mLayoutId: Int, parent: ViewGroup): View {
        return mInflater.inflate(mLayoutId, parent, false)
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_BANNER -> {
                val bannerItemData: ArrayList<HomeBean.Issue.Item> =
                    mData.take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                Observable.fromIterable(bannerItemData)
                    .subscribe { list ->
                        bannerFeedList.add(list.data?.cover?.feed ?: "")
                        bannerTitleList.add(list.data?.title ?: "")
                    }
                with(holder) {
                    getView<BGABanner>(R.id.banner).run {
                        setAutoPlayAble(bannerFeedList.size > 1)
                        setData(bannerFeedList, bannerTitleList)
                        setAdapter { banner, _, feedImageUrl, position ->
                            Glide.with(mContext)
                                .load(feedImageUrl)
                                .transition(DrawableTransitionOptions().crossFade())
                                .apply(RequestOptions().placeholder(R.drawable.placeholder_banner))
                                .into(banner.getItemImageView(position))
                        }
                    }
                }

                holder.getView<BGABanner>(R.id.banner).setDelegate { _, imageView, _, i ->
                    goToVideoPlayer(mContext as Activity, imageView, bannerItemData[i])
                }
            }
            ITEM_TYPE_TEXT_HEADER -> {
                holder.setText(R.id.tvHeader, mData[position + bannerItemSize - 1].data?.text ?: "")
            }
            ITEM_TYPE_CONTENT -> {
                setVideoItem(holder, mData[position + bannerItemSize - 1])
            }
        }
    }

    private fun setVideoItem(holder: ViewHolder, item: HomeBean.Issue.Item) {
        val itemData = item.data
        val defAvatar = R.mipmap.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = "#"

        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }

        Glide.with(mContext)
            .load(cover)
            .transition(DrawableTransitionOptions().crossFade())
            .apply(RequestOptions().placeholder(R.drawable.placeholder_banner))
            .into(holder.getView(R.id.iv_cover_feed))

        if (avatar.isNullOrEmpty()) {
            Glide.with(mContext)
                .load(defAvatar)
                .transition(DrawableTransitionOptions().crossFade())
                .apply(RequestOptions().placeholder(R.mipmap.default_avatar).circleCrop())
                .into(holder.getView(R.id.iv_avatar))
        } else {
            Glide.with(mContext)
                .load(avatar)
                .transition(DrawableTransitionOptions().crossFade())
                .apply(RequestOptions().placeholder(R.mipmap.default_avatar).circleCrop())
                .into(holder.getView(R.id.iv_avatar))
        }
        holder.setText(R.id.tv_title, itemData?.title ?: "")

        itemData?.tags?.take(4)?.forEach {
            tagText += (it.name + "/")
        }

        val timeFormat = durationFormat(itemData?.duration ?: 0)
        tagText += timeFormat
        holder.setText(R.id.tv_tag, tagText!!)
        holder.setText(R.id.tv_category, "#" + itemData?.category)
        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), item)
        })
    }

    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        activity.startActivity(intent)
    }
}
