package com.example.myspacexdemoapp.ui

import android.graphics.Insets.add
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.BuildConfig
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.SpaceXApi
import com.jakewharton.rxbinding4.view.clicks

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





/*        val textView: TextView = findViewById(R.id.launches)
        val button: Button = findViewById(R.id.button)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)*/

/*        button.clicks().subscribe {
            button.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            viewModel.getLaunches()
        }*/




    }
}
