package work.socialhub.kbsky.app.bsky.graph

import work.socialhub.kbsky.AbstractTest
import work.socialhub.kbsky.BlueskyFactory
import work.socialhub.kbsky.api.entity.app.bsky.graph.GraphGetFollowersRequest
import work.socialhub.kbsky.domain.Service.BSKY_SOCIAL
import kotlin.test.Test

class GetFollowersTest : AbstractTest() {

    @Test
    fun testGetFollowers() {
        val response = BlueskyFactory
            .instance(BSKY_SOCIAL.uri)
            .graph()
            .getFollowers(
                GraphGetFollowersRequest(accessJwt).also {
                    it.actor = "uakihir0.com"
                }
            )

        response.data.followers
            .forEach { print(it) }
    }
}
