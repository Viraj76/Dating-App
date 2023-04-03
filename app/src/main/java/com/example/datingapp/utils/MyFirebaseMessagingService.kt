package com.example.datingapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.datingapp.R
import com.example.datingapp.ui.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {


    private val channelId = "viraj"


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val manager = getSystemService(Context.NOTIFICATION_SERVICE)
        createNotificationChannel(manager as NotificationManager)

        val intent1 = PendingIntent.getActivities(this,0, arrayOf(intent),PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this,channelId)
            .setContentTitle(message.data["title"])
            .setContentTitle(message.data["message"])
            .setSmallIcon(R.drawable.notifications)
            .setAutoCancel(true)
            .setContentIntent(intent1)
            .build()

        manager.notify(Random.nextInt(),notification)

    }



    private fun createNotificationChannel(manager : NotificationManager) {
        val channel = NotificationChannel(channelId, "virajchat",
        NotificationManager.IMPORTANCE_HIGH)

        channel.description = "New Chat"
        channel.enableLights( true)
        manager.createNotificationChannel(channel)

    }

}