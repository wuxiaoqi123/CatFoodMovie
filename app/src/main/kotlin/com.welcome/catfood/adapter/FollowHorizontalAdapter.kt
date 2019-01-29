package com.welcome.catfood.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import android.widget.ImageView
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
 *     time   : 2019/01/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class FollowHorizontalAdapter(
    mContext: Context,
    categoryList: ArrayList<HomeBean.Issue.Item>,
    layoutId: Int
) : CommonAdapter<HomeBean.Issue.Item>(mContext, categoryList, layoutId) {

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        val horizontalItemData = data.data
        holder.setImagePath(
            R.id.iv_cover_feed,
            object : ViewHolder.HolderImageLoader(data.data?.cover?.feed!!) {
                override fun loadImage(iv: ImageView, path: String) {
                    // 加载封页图
                    Glide.with(mContext)
                        .load(path)
                        .apply(RequestOptions().placeholder(R.drawable.placeholder_banner))
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(holder.getView<ImageView>(R.id.iv_cover_feed))
                }
            })
        holder.setText(R.id.tv_title, horizontalItemData?.title ?: "")
        val timeFormat = durationFormat(horizontalItemData!!.duration)
        //标签
        with(holder) {
            if (horizontalItemData.tags.size > 0) {
                setText(R.id.tv_tag, "#${horizontalItemData.tags[0].name} / $timeFormat")
            } else {
                setText(R.id.tv_tag, "#$timeFormat")
            }
            setOnItemClickListener(listener = View.OnClickListener {
                goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), data)
            })
        }
    }

    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(VideoDetailActivity.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
}
