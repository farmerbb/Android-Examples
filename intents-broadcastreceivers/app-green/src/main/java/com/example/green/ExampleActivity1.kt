package com.example.green

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.example_activity_1.*

class ExampleActivity1: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example_activity_1)

        button.setOnClickListener {
            val intent = Intent(this, ExampleActivity2::class.java).apply {
                putExtra("text_to_display", editText.text.toString())
            }

            startActivity(intent)
        }
    }
}
