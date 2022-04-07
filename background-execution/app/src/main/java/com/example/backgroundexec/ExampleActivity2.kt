package com.example.backgroundexec

import android.app.*
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.example_activity_2.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import androidx.core.app.NotificationCompat

class ExampleActivity2: AppCompatActivity() {

    private val notifyReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val notification = createNotification(intent?.getStringExtra("message") ?: "")

            val notificationManager = getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(456, notification)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example_activity_2)

        registerReceiver(notifyReceiver, IntentFilter(notifyAction))

        button1.setOnClickListener {
            val intent = Intent(notifyAction).apply {
                putExtra("message", editText.text.toString())
                setPackage(BuildConfig.APPLICATION_ID)
            }

            val pendingIntent = PendingIntent.getBroadcast(this, 123456, intent, PendingIntent.FLAG_CANCEL_CURRENT)

            val manager = getSystemService(ALARM_SERVICE) as AlarmManager
            val timeToNotify = System.currentTimeMillis() + (secondsEditText.text.toString().toLong() * 1000)
            manager.setExact(AlarmManager.RTC, timeToNotify, pendingIntent)

            Toast.makeText(this, R.string.message_scheduled, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(notifyReceiver)
    }

    @Suppress("DEPRECATION")
    private fun createNotification(message: String): Notification {
        val contentIntent = Intent(this, ExampleActivity2::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, ExampleApplication.channelId)
                .setSmallIcon(R.drawable.ic_message_black_24dp)
                .setContentTitle(getString(R.string.message_received))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setStyle(NotificationCompat.MessagingStyle("")
                        .addMessage(message, System.currentTimeMillis(), "Some Guy")
                )
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))

        return builder.build()
    }

    companion object {
        const val notifyAction = "com.example.backgroundexec.NOTIFY_MESSAGE"
    }
}
