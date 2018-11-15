package com.example.blue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.example_activity_2.*

class ExampleActivity2: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example_activity_2)

        val textToDisplay = intent.getStringExtra("text_to_display")
        textView.text = textToDisplay
    }
}
