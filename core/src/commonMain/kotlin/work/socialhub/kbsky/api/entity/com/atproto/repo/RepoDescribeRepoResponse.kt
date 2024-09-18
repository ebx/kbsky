package work.socialhub.kbsky.api.entity.com.atproto.repo

import kotlinx.serialization.Serializable
import work.socialhub.kbsky.model.com.atproto.server.DidDocUnion

@Serializable
data class RepoDescribeRepoResponse(
    val handle: String,
    val did: String,
    val didDoc: DidDocUnion? = null,
    val collections: List<String>? = null,
    val handleIsCorrect: Boolean = false,
)