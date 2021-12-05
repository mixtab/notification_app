package ua.com.tabarkevych.pecoade_app.util

import android.content.Context

import androidx.core.app.NotificationCompat

import android.content.Context.NOTIFICATION_SERVICE


import androidx.core.app.NotificationManagerCompat
import android.app.NotificationManager

import android.app.NotificationChannel

import android.os.Build
import androidx.navigation.NavDeepLinkBuilder
import ua.com.tabarkevych.pecoade_app.R
import ua.com.tabarkevych.pecoade_app.ui.main.MainFragmentArgs


object NotificationUtil {
    fun createViewPagerNotification(positionId: Int, context: Context) {

        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.root_nav)
            .setDestination(R.id.mainFragment)
            .setArguments(
                MainFragmentArgs.Builder()
                    .setPosition(positionId - 1)
                    .build()
                    .toBundle()
            )
            .createPendingIntent()

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_ellipse)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text, positionId.toString()))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = context.getString(R.string.notification_channel_id)

            val channel = NotificationChannel(
                channelId,
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channelId)
        }

        with(NotificationManagerCompat.from(context)) {
            notify(positionId, builder.build())
        }
    }


}