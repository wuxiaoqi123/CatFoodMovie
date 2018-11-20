package com.welcome.catfood.bean

import com.welcome.catfood.base.BaseBean
import java.io.Serializable

/**
 * desc: 首页 Bean（视频详情，相关等）
 */
data class HomeBean(
    val issueList: ArrayList<Issue>,
    val nextPageUrl: String,
    val nextPublishTime: Long,
    val newestIssueType: String,
    val dialog: Any
) : BaseBean() {

    data class Issue(
        val releaseTime: Long,
        val type: String,
        val date: Long,
        val total: Int,
        val publishTime: Long,
        val itemList: ArrayList<Item>,
        var count: Int,
        val nextPageUrl: String
    ) : BaseBean() {
        data class Item(val type: String, val data: Data?, val tag: String) : Serializable {

            data class Data(
                val dataType: String,
                val text: String,
                val videoTitle: String,
                val id: Long,
                val title: String,
                val slogan: String?,
                val description: String,
                val actionUrl: String,
                val provider: Provider,
                val category: String,
                val parentReply: ParentReply,
                val author: Author,
                val cover: Cover,
                val likeCount: Int,
                val playUrl: String,
                val thumbPlayUrl: String,
                val duration: Long,
                val message: String,
                val createTime: Long,
                val webUrl: WebUrl,
                val library: String,
                val user: User,
                val playInfo: ArrayList<PlayInfo>?,
                val consumption: Consumption,
                val campaign: Any,
                val waterMarks: Any,
                val adTrack: Any,
                val tags: ArrayList<Tag>,
                val type: String,
                val titlePgc: Any,
                val descriptionPgc: Any,
                val remark: String,
                val idx: Int,
                val shareAdTrack: Any,
                val favoriteAdTrack: Any,
                val webAdTrack: Any,
                val date: Long,
                val promotion: Any,
                val label: Any,
                val labelList: Any,
                val descriptionEditor: String,
                val collected: Boolean,
                val played: Boolean,
                val subtitles: Any,
                val lastViewTime: Any,
                val playlists: Any,
                val header: Header,
                val itemList: ArrayList<HomeBean.Issue.Item>
            ) : BaseBean() {
                data class Tag(val id: Int, val name: String, val actionUrl: String, val adTrack: Any) : BaseBean()

                data class Author(val icon: String, val name: String, val description: String) : BaseBean()

                data class Provider(val name: String, val alias: String, val icon: String) : BaseBean()

                data class Cover(
                    val feed: String, val detail: String,
                    val blurred: String, val sharing: String, val homepage: String
                ) : BaseBean()

                data class WebUrl(val raw: String, val forWeibo: String) : BaseBean()

                data class PlayInfo(val name: String, val url: String, val type: String, val urlList: ArrayList<Url>) :
                    BaseBean()

                data class Consumption(val collectionCount: Int, val shareCount: Int, val replyCount: Int) : BaseBean()

                data class User(
                    val uid: Long,
                    val nickname: String,
                    val avatar: String,
                    val userType: String,
                    val ifPgc: Boolean
                ) : BaseBean()

                data class ParentReply(val user: User, val message: String) : BaseBean()

                data class Url(val size: Long) : BaseBean()

                data class Header(
                    val id: Int,
                    val icon: String,
                    val iconType: String,
                    val description: String,
                    val title: String,
                    val font: String,
                    val cover: String,
                    val label: Label,
                    val actionUrl: String,
                    val subtitle: String,
                    val labelList: ArrayList<Label>
                ) : BaseBean() {
                    data class Label(val text: String, val card: String, val detial: Any, val actionUrl: Any) :
                        BaseBean()
                }
            }
        }
    }
}