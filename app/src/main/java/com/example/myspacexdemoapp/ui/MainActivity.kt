package com.example.myspacexdemoapp.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.ui.launch.DetailActivity
import com.example.myspacexdemoapp.ui.launches.MainFragment

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
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        title = getString(R.string.title)
        startActivity(Intent(this,DetailActivity::class.java))
    }
}
