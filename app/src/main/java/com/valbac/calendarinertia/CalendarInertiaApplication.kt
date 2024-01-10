package com.valbac.calendarinertia

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CalendarInertiaApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "reminder",
            "Reminder Channel",
            NotificationManager.IMPORTANCE_HIGH
        )

        channel.description = "A notification channel for reminder"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}