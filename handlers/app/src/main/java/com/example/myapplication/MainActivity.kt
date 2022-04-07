package com.example.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, getExampleClass()))
        finish()
    }

    fun getExampleClass(): Class<*> {
        return ExampleActivity1::class.java
//        return ExampleActivity2::class.java
//        return ExampleActivity3::class.java
//        return ExampleActivity4::class.java
    }
}
