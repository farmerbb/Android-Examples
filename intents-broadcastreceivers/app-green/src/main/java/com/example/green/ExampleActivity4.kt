package com.example.green

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.example_activity_4.*

class ExampleActivity4: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example_activity_4)

        button1.setOnClickListener {
            val intent = Intent("com.example.blue.RECEIVE_TEXT").apply {
                setPackage("com.example.blue")
                putExtra("text_to_display", editText.text.toString())
            }

            sendBroadcast(intent)
        }

        button2.setOnClickListener {
            val intent = Intent("com.example.blue.RECEIVE_TEXT_ACTIVITY").apply {
                putExtra("text_to_display", editText.text.toString())
            }

            sendBroadcast(intent)
        }
    }
}
