package work.socialhub.kbsky.api.entity.app.bsky.graph

import work.socialhub.kbsky.api.entity.share.AuthRequest
import work.socialhub.kbsky.api.entity.share.MapRequest
import work.socialhub.kbsky.auth.AuthProvider

class GraphGetStarterPackRequest (
    auth: AuthProvider
) : AuthRequest(auth), MapRequest {

    var starterPack: String? = null

    override fun toMap(): Map<String, Any> {
        return mutableMapOf<String, Any>().also {
            it.addParam("starterPack", starterPack)
        }
    }
}
