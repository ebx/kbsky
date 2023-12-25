package work.socialhub.kbsky.model.bsky.feed

import kotlinx.serialization.Serializable

@Serializable
class FeedDefsFeedViewPost {
    lateinit var post: FeedDefsPostView
    var reply: FeedDefsReplyRef? = null
    var reason: FeedDefsReasonRepost? = null
}
