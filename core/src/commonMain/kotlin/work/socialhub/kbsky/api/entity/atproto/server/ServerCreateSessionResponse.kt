package work.socialhub.kbsky.api.entity.atproto.server

import kotlinx.serialization.Serializable
import work.socialhub.kbsky.model.atproto.server.DidDocUnion

@Serializable
class ServerCreateSessionResponse {
    lateinit var accessJwt: String
    lateinit var refreshJwt: String
    lateinit var handle: String
    lateinit var did: String

    var email: String? = null
    var emailConfirmed: Boolean? = null
    var didDoc: DidDocUnion? = null
}
