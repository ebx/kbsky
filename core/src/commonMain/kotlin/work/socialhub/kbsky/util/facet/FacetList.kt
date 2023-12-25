package work.socialhub.kbsky.util.facet

import work.socialhub.kbsky.model.bsky.richtext.RichtextFacet
import work.socialhub.kbsky.model.bsky.richtext.RichtextFacetByteSlice
import work.socialhub.kbsky.model.bsky.richtext.RichtextFacetLink
import work.socialhub.kbsky.model.bsky.richtext.RichtextFacetMention

class FacetList(
    val records: List<FacetRecord>
) {
    fun displayText(): String {
        return records
            .map { it.displayText }
            .joinToString("")
    }

    /**
     * RichtextFacet のリストに変換
     * Convert to RichtextFacet List
     */
    fun richTextFacets(
        handleToDidMap: Map<String, String>
    ): List<RichtextFacet> {

        var bytes = 0
        val facets = mutableListOf<RichtextFacet>()
        val byteCount: (String) -> Int = { it.encodeToByteArray().size }

        for (record in records) {
            when (record.type) {
                FacetType.Text -> {
                    bytes += byteCount(record.displayText)
                }

                FacetType.Mention ->
                    // DID が設定されている場合はリンクとして Facet を準備
                    if (handleToDidMap.containsKey(record.displayText)) {

                        val slice = RichtextFacetByteSlice()
                        slice.byteStart = bytes
                        bytes += byteCount(record.displayText)
                        slice.byteEnd = bytes

                        val facet = RichtextFacet()
                        facet.features = mutableListOf()
                        facet.index = slice

                        val mention = RichtextFacetMention()
                        mention.did = handleToDidMap[record.displayText]
                        facet.features!!.add(mention)
                        facets.add(facet)

                    } else {
                        // DID が設定されていない場合は単純なテキストとして表示
                        bytes += byteCount(record.displayText)
                    }

                FacetType.Link -> {
                    val slice = RichtextFacetByteSlice()
                    slice.byteStart = bytes
                    bytes += byteCount(record.displayText)
                    slice.byteEnd = bytes

                    val facet = RichtextFacet()
                    facet.features = mutableListOf()
                    facet.index = slice

                    val mention = RichtextFacetLink()
                    mention.uri = record.contentText
                    facet.features!!.add(mention)
                    facets.add(facet)
                }
            }
        }
        return facets
    }
}
