package work.socialhub.kbsky.stream

import kbsky.stream.api.atproto.SyncResource

interface ATProtocolStream {
    fun sync(): SyncResource
}
