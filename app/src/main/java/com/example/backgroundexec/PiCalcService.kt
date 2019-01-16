package com.example.backgroundexec

import android.app.Service
import android.content.Intent
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import androidx.core.content.ContextCompat
import android.app.Notification
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter

class PiCalcService: Service(), CoroutineScope {
    lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val stopReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            stopSelf()
        }
    }

    override fun onBind(intent: Intent?) = null
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val iterations = intent.getLongExtra("iterations", 0)
        calculatePi(iterations)

        return Service.START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        job = Job()

        registerReceiver(stopReceiver, IntentFilter(stopIntentAction))

        val notification = createForegroundNotification()
        startForeground(31415926, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()

        unregisterReceiver(stopReceiver)
    }

    private fun createForegroundNotification(): Notification {
        val actionIntent = Intent(stopIntentAction)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        
        val builder = NotificationCompat.Builder(this, ExampleApplication.channelId)
                .setSmallIcon(R.drawable.pi)
                .setContentTitle(getString(R.string.calculating_pi))
                .addAction(0, getString(R.string.stop), pendingIntent)
                .setProgress(0, 0, true)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setShowWhen(false)
                .setOngoing(true)

        return builder.build()
    }

    private fun createResultNotification(result: Double): Notification {
        val contentIntent = Intent(this, ExampleActivity1::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, ExampleApplication.channelId)
                .setSmallIcon(R.drawable.pi)
                .setContentTitle(getString(R.string.pi_calculated))
                .setContentText("$result")
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setShowWhen(false)

        return builder.build()
    }

    private fun calculatePi(iterations: Long) = launch {
        val calculation = async(Dispatchers.IO) {
            var iterationCount = 0L
            var denominator = 1.0
            var shouldAdd = true
            var result = 0.0

            while(iterationCount < iterations) {
                iterationCount++

                val coefficient = if(shouldAdd) 1.0 else -1.0
                result += coefficient * (4.0 / denominator)
                denominator += 2.0
                shouldAdd = !shouldAdd

                println("Iteration $iterationCount of $iterations: $result")
            }

            result
        }

        val pi = calculation.await()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(123, createResultNotification(pi))

        stopSelf()
    }
    
    companion object {
        const val stopIntentAction = "com.example.backgroundexec.STOP_SERVICE"
    }
}