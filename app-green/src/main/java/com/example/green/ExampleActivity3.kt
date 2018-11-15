package com.example.green

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.example_activity_3.*

class ExampleActivity3: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example_activity_3)

        button1.setOnClickListener {
            val intent = Intent().apply {
                component = ComponentName.unflattenFromString("com.example.blue/.ExampleActivity2")
                putExtra("text_to_display", editText.text.toString())
            }

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Activity not found!", Toast.LENGTH_SHORT).show()
            }
        }

        button2.setOnClickListener {
            val intent = Intent("com.example.blue.EXAMPLE_ACTION").apply {
                addCategory("com.example.blue.EXAMPLE_CATEGORY")
                putExtra("text_to_display", editText.text.toString())
            }

            intent.resolveActivity(packageManager)?.let {
                startActivity(intent)
            }
        }

        button3.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            intent.resolveActivity(packageManager)?.let {
                startActivity(intent)
            }
        }
    }
}
