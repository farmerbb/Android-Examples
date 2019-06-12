package com.example.graphqlapp

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE

class MainActivity: AppCompatActivity() {

    private val viewId = View.generateViewId()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contentView = FrameLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            id = viewId
        }

        setContentView(contentView)

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

            replace(viewId, fragment)
            commit()
        }
    }

    override fun onBackPressed() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onBackPressed()
    }
}
