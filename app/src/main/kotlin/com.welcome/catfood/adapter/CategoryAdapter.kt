package com.welcome.catfood.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.welcome.catfood.R
import com.welcome.catfood.activity.CategoryDetailActivity
import com.welcome.catfood.bean.CategoryBean

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2019/01/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CategoryAdapter(mContext: Context, categoryList: ArrayList<CategoryBean>, layoutId: Int) :
    CommonAdapter<CategoryBean>(mContext, categoryList, layoutId) {

    fun setData(categoryList: ArrayList<CategoryBean>) {
        mData.clear()
        mData.addAll(categoryList)
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, data: CategoryBean, position: Int) {
        holder.setText(R.id.tv_category_name, "#${data.name}")
        holder.setImagePath(
            R.id.iv_category,
            object : ViewHolder.HolderImageLoader(data.bgPicture) {
                override fun loadImage(iv: ImageView, path: String) {
                    Glide.with(mContext)
                        .load(path)
                        .apply(RequestOptions().placeholder(R.color.color_darker_gray))
                        .transition(DrawableTransitionOptions().crossFade())
                        .thumbnail(0.5f)
                        .into(iv)
                }
            })

        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                CategoryDetailActivity.start(mContext as Activity, data)
            }
        })
    }
}
