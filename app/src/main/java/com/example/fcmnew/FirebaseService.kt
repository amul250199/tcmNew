package com.example.fcmnew

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

private const val CHANNEL_ID="my_channel"

class FirebaseService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        val intent=Intent(this,MainActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId =  Random.nextInt()


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            createNotificationChannel(notificationManager)
        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent=PendingIntent.getActivity(this,8,intent,FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(p0.data["title"])
            .setContentText(p0.data["message"])
            .setSmallIcon(R.drawable.ic_baseline_audiotrack_24)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationId,notification)

    }

    private fun createNotificationChannel(notificationManager: NotificationManager){
        val channelName="channelName"
        val channel = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel(CHANNEL_ID,channelName,IMPORTANCE_HIGH).apply { description="My Channel description"
            enableLights(true)
            lightColor= Color.GREEN
            }
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        notificationManager.createNotificationChannel(channel)
    }
}