package com.valbac.calendarinertia.core

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.valbac.calendarinertia.MainActivity
import com.valbac.calendarinertia.R
import kotlin.random.Random
class AlarmReceiver : BroadcastReceiver () {
    private fun showNotification(context: Context?, message: String) {
        val notification = context?.let {
            NotificationCompat.Builder(it, "reminder")
                .setContentText(message)
                .setContentTitle("Task Reminder")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(
                    PendingIntent.getActivity(
                        it,
                        0,
                        Intent(it, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        },
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                )
                .setAutoCancel(true)
                .build()
        }
        context?.getSystemService(NotificationManager::class.java)?.notify(
            Random.nextInt(),
            notification
        )
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        showNotification(context, message)
    }
}