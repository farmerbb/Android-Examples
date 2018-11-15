package com.example.blue

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.example_activity_1.*

class ExampleActivity1: AppCompatActivity() {

    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val textToDisplay = intent?.getStringExtra("text_to_display")
            textView.text = textToDisplay
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example_activity_1)
    }

    override fun onStart() {
        super.onStart()

        val filter = IntentFilter("com.example.blue.RECEIVE_TEXT_ACTIVITY")
        registerReceiver(receiver, filter)
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(receiver)
    }
}
