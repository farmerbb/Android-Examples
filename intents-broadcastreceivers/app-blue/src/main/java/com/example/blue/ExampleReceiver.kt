package com.example.blue

import android.content.*
import android.widget.Toast

class ExampleReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val textToDisplay = intent?.getStringExtra("text_to_display")
        Toast.makeText(context, textToDisplay, Toast.LENGTH_SHORT).show()
    }
}
