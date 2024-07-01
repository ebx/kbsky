package work.socialhub.kbsky.model.app.bsky.embed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kbsky.BlueskyTypes
import work.socialhub.kbsky.model.com.atproto.repo.RepoStrongRef

@Serializable
class EmbedRecord : EmbedUnion() {

    companion object {
        const val TYPE = BlueskyTypes.EmbedRecord
    }

    @SerialName("\$type")
    override var type = TYPE

    var record: RepoStrongRef? = null
}
