package com.example.backgroundexec

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, getExampleClass()))
        finish()
    }

    fun getExampleClass(): Class<*> {
        return ExampleActivity1::class.java
//        return ExampleActivity2::class.java
    }
}
