package work.socialhub.kbsky.model.app.bsky.labeler

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kbsky.util.json.LabelerViewPolymorphicSerializer

/**
 * @see LabelerView
 * @see LabelerViewDetailed
 */
@Serializable(with = LabelerViewPolymorphicSerializer::class)
abstract class LabelerViewUnion {
    @SerialName("\$type")
    abstract var type: String

    val asLabelerView get() = this as? LabelerView
    val asLabelerViewDetailed get() = this as? LabelerViewDetailed
}
