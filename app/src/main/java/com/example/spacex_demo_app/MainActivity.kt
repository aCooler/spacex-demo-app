package com.example.spacex_demo_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloClient
import com.example.spacex_demo_app.api.SpaceXApi
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val lim: Int = 100
    private var disposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.launches)

        val apolloClient =
            ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
        var text = ""
        disposable = CompositeDisposable()
        disposable?.add(SpaceXApi(apolloClient).getLaunches().subscribe { resp ->
            text += resp.data?.launches?.get(0)?.details?.take(lim)
            textView.text = text
        })

        disposable?.add(SpaceXApi(apolloClient).getLaunchById("9").subscribe { resp ->
            text += resp.data?.launch?.details?.take(lim)
            textView.text = text
        })
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
