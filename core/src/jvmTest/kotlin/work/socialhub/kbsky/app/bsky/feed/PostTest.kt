package work.socialhub.kbsky.app.bsky.feed

import work.socialhub.kbsky.ATProtocolFactory
import work.socialhub.kbsky.AbstractTest
import work.socialhub.kbsky.BlueskyFactory
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedDeletePostRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedPostRequest
import work.socialhub.kbsky.api.entity.com.atproto.identity.IdentityResolveHandleRequest
import work.socialhub.kbsky.api.entity.com.atproto.repo.RepoUploadBlobRequest
import work.socialhub.kbsky.domain.Service.BSKY_SOCIAL
import work.socialhub.kbsky.internal.share._InternalUtility.toJson
import work.socialhub.kbsky.model.com.atproto.repo.RepoStrongRef
import work.socialhub.kbsky.util.facet.FacetType
import work.socialhub.kbsky.util.facet.FacetUtil
import kotlin.test.Test

class PostTest : AbstractTest() {

    @Test
    fun testPost() {
        val response = BlueskyFactory
            .instance(BSKY_SOCIAL.uri)
            .feed()
            .post(
                FeedPostRequest(accessJwt).also {
                    it.text = "テスト投稿"
                }
            )

        println(response.data.uri)
    }

    @Test
    fun testFeedPostWithImage() {
        val stream = javaClass.getResourceAsStream("/image/icon.png")
        checkNotNull(stream)

        // Upload Image
        val response1 = ATProtocolFactory
            .instance(BSKY_SOCIAL.uri)
            .repo()
            .uploadBlob(
                RepoUploadBlobRequest(
                    accessJwt = accessJwt,
                    name = "icon.png",
                    bytes = stream.readBytes(),
                    contentType = "image/jpeg"
                )
            )

        val link = checkNotNull(response1.data.blob.ref?.link)
        println(link)

        // Setup Image
        val imagesMain = work.socialhub.kbsky.model.app.bsky.embed.EmbedImages()
        run {
            val images = mutableListOf<work.socialhub.kbsky.model.app.bsky.embed.EmbedImagesImage>()
            imagesMain.images = images

            val image = work.socialhub.kbsky.model.app.bsky.embed.EmbedImagesImage()
            image.image = response1.data.blob
            image.alt = "image test"
            images.add(image)
        }

        println(toJson(imagesMain))

        // Post With Image
        val response2 = BlueskyFactory
            .instance(BSKY_SOCIAL.uri)
            .feed()
            .post(
                FeedPostRequest(accessJwt).also {
                    it.text = "画像投稿テスト"
                    it.embed = imagesMain
                }
            )

        println(response2.data.uri)
    }

    @Test
    fun testFeedPostReplay() {

        val root = BlueskyFactory
            .instance(BSKY_SOCIAL.uri)
            .feed().post(
                FeedPostRequest(accessJwt).also {
                    it.text = "リプライテスト (ルート)"
                }
            )

        val parentFun = {
            val rootRef = RepoStrongRef(
                checkNotNull(root.data.uri),
                checkNotNull(root.data.cid),
            )
            val parentRef = RepoStrongRef(
                checkNotNull(root.data.uri),
                checkNotNull(root.data.cid),
            )

            val reply = work.socialhub.kbsky.model.app.bsky.feed.FeedPostReplyRef().also {
                it.parent = parentRef
                it.root = rootRef
            }

            BlueskyFactory
                .instance(BSKY_SOCIAL.uri)
                .feed().post(
                    FeedPostRequest(accessJwt).also {
                        it.text = "リプライテスト (親)"
                        it.reply = reply
                    }
                )
        }

        val parent = parentFun()
        println(parent.data.uri)

        val lastFun = {
            val rootRef = RepoStrongRef(
                checkNotNull(parent.data.uri),
                checkNotNull(parent.data.cid),
            )
            val parentRef = RepoStrongRef(
                checkNotNull(parent.data.uri),
                checkNotNull(parent.data.cid),
            )

            val reply = work.socialhub.kbsky.model.app.bsky.feed.FeedPostReplyRef().also {
                it.parent = parentRef
                it.root = rootRef
            }

            BlueskyFactory
                .instance(BSKY_SOCIAL.uri)
                .feed()
                .post(
                    FeedPostRequest(accessJwt).also {
                        it.text = "リプライテスト (子)"
                        it.reply = reply
                    }
                )
        }

        val last = lastFun()
        println(last.data.uri)
    }


    @Test
    fun testPostWithFacets() {

        val test = "@uakihir0.com Facet のテスト投稿 https://www.uakihir0.com/blog/p/202305-mario-movie/"
        val list = FacetUtil.extractFacets(test)

        val handles = list.records
            .filter { it.type === FacetType.Mention }
            .map { it.displayText }

        val handleToDidMap = mutableMapOf<String, String>()

        for (handle in handles) {
            val response = BlueskyFactory
                .instance(BSKY_SOCIAL.uri)
                .identity()
                .resolveHandle(
                    IdentityResolveHandleRequest().also {
                        it.handle = handle.substring(1)
                    }
                )
            handleToDidMap[handle] = checkNotNull(response.data.did)
        }

        val facets = list.richTextFacets(handleToDidMap)

        BlueskyFactory
            .instance(BSKY_SOCIAL.uri)
            .feed()
            .post(
                FeedPostRequest(accessJwt).also {
                    it.text = list.displayText()
                    it.facets = facets
                }
            )
    }

    @Test
    fun testDeleteFeed() {

        // Create
        val response = BlueskyFactory
            .instance(BSKY_SOCIAL.uri)
            .feed()
            .post(
                FeedPostRequest(accessJwt).also {
                    it.text = "テスト（すぐ消す）"
                }
            )

        val uri = checkNotNull(response.data.uri)

        // Delete
        BlueskyFactory
            .instance(BSKY_SOCIAL.uri)
            .feed()
            .deletePost(
                FeedDeletePostRequest(accessJwt).also {
                    it.uri = uri
                }
            )
    }
}
