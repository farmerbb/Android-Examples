package com.example.backgroundexec

import android.app.*
import android.os.Build

class ExampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return

        val name = getString(R.string.app_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val notificationManager = getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(NotificationChannel(channelId, name, importance))
    }

    companion object {
        const val channelId = "example_notification_channel"
    }
}