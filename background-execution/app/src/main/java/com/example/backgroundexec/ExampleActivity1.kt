package com.example.backgroundexec

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.example_activity_1.*

class ExampleActivity1: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example_activity_1)

        button.setOnClickListener {
            val intent = Intent(this, PiCalcService::class.java).apply {
                putExtra("iterations", editText.text.toString().toLong())
            }

            stopService(intent)

            ContextCompat.startForegroundService(this, intent)
            Toast.makeText(this, R.string.pull_down_notification_drawer, Toast.LENGTH_SHORT).show()
        }
    }
}
