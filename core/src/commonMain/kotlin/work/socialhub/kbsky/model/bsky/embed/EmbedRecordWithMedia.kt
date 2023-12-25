package work.socialhub.kbsky.model.bsky.embed

import kotlinx.serialization.Serializable
import work.socialhub.kbsky.BlueskyTypes

@Serializable
class EmbedRecordWithMedia : EmbedUnion {

    companion object {
        const val TYPE = BlueskyTypes.EmbedRecordWithMedia
    }

    override var type = TYPE

    var record: EmbedRecord? = null

    /** only external and images  */
    var media: EmbedUnion? = null
}
