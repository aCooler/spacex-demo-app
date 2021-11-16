package com.example.spacex_demo_app

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.spacex_demo_app.api.Client
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import android.os.Handler as Handler
import com.apollographql.apollo.ApolloCall as ApolloCall

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.launches)
        val handler: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                textView?.text = msg.data.getString("launch")
            }
        }
        val client = Client(handler)
        client.initialise()

    }


}


