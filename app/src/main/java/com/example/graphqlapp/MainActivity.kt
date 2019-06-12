package com.example.graphqlapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null)
            navigateTo(ArtistSearchFragment(), false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                // Override default Android "up" behavior to instead mimic the back button
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun navigateTo(fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction().apply {
            if(addToBackStack) {
                setTransition(TRANSIT_FRAGMENT_FADE)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                addToBackStack(null)
            }

            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

    override fun onBackPressed() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onBackPressed()
    }
}
