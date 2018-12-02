package com.welcome.catfood.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.welcome.catfood.R
import com.welcome.catfood.bean.HomeBean

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

    }
}
