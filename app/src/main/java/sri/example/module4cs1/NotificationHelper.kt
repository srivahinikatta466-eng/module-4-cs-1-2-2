// app/src/main/java/project/background/NotificationHelper.kt
package project.background

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationHelper {
    const val WORK_NOTIFICATION_CHANNEL_ID = "work_notifications"
    const val WORK_NOTIFICATION_CHANNEL_NAME = "Background Work"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                WORK_NOTIFICATION_CHANNEL_ID,
                WORK_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows progress of background work"
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}