package work.socialhub.kbsky.internal.share

import kotlinx.datetime.TimeZone
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import work.socialhub.kbsky.ATProtocolException
import work.socialhub.kbsky.api.entity.share.Response
import work.socialhub.kbsky.model.bsky.actor.ActorDefsPreferencesUnion
import work.socialhub.kbsky.model.bsky.embed.EmbedRecordViewUnion
import work.socialhub.kbsky.model.bsky.embed.EmbedUnion
import work.socialhub.kbsky.model.bsky.embed.EmbedViewUnion
import work.socialhub.kbsky.model.bsky.feed.FeedDefsThreadUnion
import work.socialhub.kbsky.model.bsky.richtext.RichtextFacetFeatureUnion
import work.socialhub.kbsky.model.share.RecordUnion
import work.socialhub.kbsky.util.DateFormatter
import work.socialhub.kbsky.util.json.*
import work.socialhub.khttpclient.HttpResponse

/**
 * @author uakihir0
 */
object _InternalUtility {

    val json = Json {
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            contextual(Any::class, AnySerializer)
            contextual(EmbedUnion::class, EmbedSerializer)
            contextual(EmbedViewUnion::class, EmbedViewSerializer)
            contextual(EmbedRecordViewUnion::class, EmbedRecordViewSerializer)

            contextual(RecordUnion::class, RecordSerializer)
            contextual(FeedDefsThreadUnion::class, FeedDefsThreadSerializer)
            contextual(RichtextFacetFeatureUnion::class, RichtextFacetFeatureSerializer)
            contextual(ActorDefsPreferencesUnion::class, ActorDefsPreferencesSerializer)
        }
    }

    inline fun <reified T> toJson(obj: T): String {
        return json.encodeToString(obj)
    }

    inline fun <reified T> fromJson(obj: String): T {
        return json.decodeFromString(obj)
    }

    val dateFormat = DateFormatter(
        format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        timezone = TimeZone.UTC,
    )

    fun proceedUnit(function: () -> HttpResponse): Response<Unit> {
        try {
            val response: HttpResponse = function()
            if (response.status == 200) {
                return Response(Unit)
            }
            // TODO: include error response in exception
            throw ATProtocolException(null)
        } catch (e: Exception) {
            throw handleError(e)
        }
    }

    inline fun <reified T> proceed(function: () -> HttpResponse): Response<T> {
        try {
            val response: HttpResponse = function()
            if (response.status == 200) {
                println(response.stringBody())
                return Response(response.typedBody(json))
            }
            // TODO: include error response in exception
            throw ATProtocolException(null)
        } catch (e: Exception) {
            throw handleError(e)
        }
    }

    fun xrpc(uri: String): String {
        var url = uri
        if (!uri.endsWith("/")) {
            url += "/"
        }
        url += "xrpc/"
        return url
    }

    fun handleError(e: Exception): RuntimeException {
        /*
        return try {
            val message: String = e.getResponse().asString()
            val error: Map<String, Any> = gson.fromJson(
                message,
                object : TypeToken<Map<String?, Any?>?>() {}.getType()
            )
            val exception = ATProtocolException(e)
            exception.setErrorMessage(error["message"].toString())
            exception.setError(error["error"].toString())
            exception
        } catch (t: java.lang.Exception) {
            ATProtocolException(e)
        }
        */
        return ATProtocolException(e)
    }
}
