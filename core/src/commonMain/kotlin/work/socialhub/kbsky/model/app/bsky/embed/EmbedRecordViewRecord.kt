package work.socialhub.kbsky.model.app.bsky.embed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kbsky.BlueskyTypes
import work.socialhub.kbsky.model.com.atproto.label.LabelDefsLabel
import work.socialhub.kbsky.model.app.bsky.actor.ActorDefsProfileViewBasic
import work.socialhub.kbsky.model.share.RecordUnion

@Serializable
class EmbedRecordViewRecord : work.socialhub.kbsky.model.app.bsky.embed.EmbedRecordViewUnion() {

    companion object {
        const val TYPE = BlueskyTypes.EmbedRecord + "#viewRecord"
    }

    @SerialName("\$type")
    override var type = work.socialhub.kbsky.model.app.bsky.embed.EmbedRecordViewRecord.Companion.TYPE

    var uri: String? = null
    var cid: String? = null
    var author: work.socialhub.kbsky.model.app.bsky.actor.ActorDefsProfileViewBasic? = null
    var value: RecordUnion? = null
    var labels: List<LabelDefsLabel>? = null
    var embeds: List<work.socialhub.kbsky.model.app.bsky.embed.EmbedViewUnion>? = null
    var indexedAt: String? = null
}
