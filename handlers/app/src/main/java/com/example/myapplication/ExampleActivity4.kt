package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class ExampleActivity4: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loading.visibility = View.VISIBLE

        val handler = Handler()

        GlobalScope.launch {
            delay(2000)

            done.visibility = View.VISIBLE
//            handler.post { done.visibility = View.VISIBLE }
//            runOnUiThread { done.visibility = View.VISIBLE }
        }
    }
}
