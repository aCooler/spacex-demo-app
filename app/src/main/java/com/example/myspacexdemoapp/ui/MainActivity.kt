package com.example.myspacexdemoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.myspacexdemoapp.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                addToBackStack("main_fragment")
                replace<MainFragment>(R.id.fragment_container)
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.title)
    }
}
