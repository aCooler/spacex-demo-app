package com.example.myspacexdemoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.ui.launches.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                val mainFragment = MainFragment()
                add(R.id.fragment_container, mainFragment)
            }
        }
    }
}
