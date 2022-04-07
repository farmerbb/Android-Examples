package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.test_layout.*

class ExampleActivity3: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout)

        scrollView.scrollTo(10000, 10000)
//        Handler().postDelayed({ scrollView.scrollTo(10000, 10000) }, 100)
    }
}
