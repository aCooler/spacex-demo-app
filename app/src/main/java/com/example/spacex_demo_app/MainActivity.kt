package com.example.spacex_demo_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloClient
import com.example.spacex_demo_app.api.SpaceXApi
import io.reactivex.rxjava3.disposables.Disposable

class MainActivity : AppCompatActivity() {

    private val lim: Int = 100
    private var disposable: Disposable? = null
    private var disposable1: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.launches)

        val apolloClient =
            ApolloClient.builder().serverUrl("https://api.spacex.land/graphql/").build()
        var text = ""
        disposable = SpaceXApi(apolloClient).getLaunches().subscribe { resp ->
            text += resp.data?.launches?.get(0)?.details?.take(lim)
            textView.text = text
        }

        disposable1 = SpaceXApi(apolloClient).getLaunchById("9").subscribe { resp ->
            text += resp.data?.launch?.details?.take(lim)
            textView.text = text
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable1?.dispose()
        super.onDestroy()
    }
}
