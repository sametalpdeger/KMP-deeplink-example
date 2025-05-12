package org.example.deeplink

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.core.net.toUri

class DeepLinkApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
        showNotification()
    }


    private fun createNotificationChannel() {
        // Only create NotificationChannel for API 26 and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id",
                "channel_name",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService<NotificationManager>()
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun showNotification() {
        val activityIntent = Intent(this, MainActivity::class.java).apply {
            data = "http://192.168.1.113:4050/deeplink/dsadasdsa".toUri()
        }
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(activityIntent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }

        // Use channel ID only for API 26+, omit for lower APIs
        val builder = NotificationCompat.Builder(
            this,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) "channel_id" else ""
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Notification Title")
            .setContentText("Notification Text")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService<NotificationManager>()
        notificationManager?.notify(0, builder.build())
    }
}