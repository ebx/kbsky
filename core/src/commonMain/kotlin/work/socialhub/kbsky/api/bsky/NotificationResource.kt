package work.socialhub.kbsky.api.bsky

import work.socialhub.kbsky.api.entity.bsky.notification.*
import work.socialhub.kbsky.api.entity.share.Response


/**
 * Bluesky/Notification
 * [Reference](https://atproto.com/lexicons/app-bsky-notification)
 */
interface NotificationResource {

    /**
     * Get the number of unread notifications.
     */
    fun getUnreadCount(
        request: NotificationGetUnreadCountRequest
    ): Response<NotificationGetUnreadCountResponse>

    /**
     * List notifications.
     */
    fun listNotifications(
        request: NotificationListNotificationsRequest
    ): Response<NotificationListNotificationsResponse>

    /**
     * Notify server that the user has seen notifications.
     */
    fun updateSeen(
        request: NotificationUpdateSeenRequest
    ): Response<Unit>
}
