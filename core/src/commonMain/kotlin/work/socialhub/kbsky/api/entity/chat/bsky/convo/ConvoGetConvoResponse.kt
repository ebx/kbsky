package work.socialhub.kbsky.api.entity.chat.bsky.convo

import kotlinx.serialization.Serializable
import work.socialhub.kbsky.model.chat.bsky.convo.ChatConvoDefsConvoView

@Serializable
class ConvoGetConvoResponse {
    lateinit var convo: ChatConvoDefsConvoView
}
