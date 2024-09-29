package work.socialhub.kbsky.model.app.bsky.feed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kbsky.util.json.FeedDefsReasonPolymorphicSerializer

/**
 * @see FeedDefsReasonRepost
 * @see FeedDefsReasonPin
 */
@Serializable(with = FeedDefsReasonPolymorphicSerializer::class)
abstract class FeedDefsReasonUnion {
    @SerialName("\$type")
    abstract var type: String

    val asReasonRepost get() = this as? FeedDefsReasonRepost
    val asReasonPin get() = this as? FeedDefsReasonPin
}
