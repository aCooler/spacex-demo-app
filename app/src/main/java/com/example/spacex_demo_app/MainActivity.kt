package com.example.spacex_demo_app

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.example.spacex_demo_app.api.Client
import com.example.spacex_demo_app.api.SpaceXApi
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver


class MainActivity : AppCompatActivity() {

    private var disposable:Disposable? = null
    private var disposable1:Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.launches)

        val apolloClient =
            ApolloClient.builder().serverUrl("https://api.spacex.land/graphql/").build()

        disposable = SpaceXApi(apolloClient).getLaunches().subscribe{resp ->
            textView.text = resp.data?.launches?.get(0)?.details?.take(100).toString()
        }

        disposable1 = SpaceXApi(apolloClient).getLaunchById("9").subscribe{resp ->
            textView.text =
                textView.text.toString() + resp.data?.launch?.details?.take(100).toString()
        }
    }


    override fun onDestroy() {
        disposable?.dispose()
        disposable1?.dispose()
        super.onDestroy()
    }

}



