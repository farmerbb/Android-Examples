package com.example.green

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
//        return ExampleActivity3::class.java
//        return ExampleActivity4::class.java
    }
}
