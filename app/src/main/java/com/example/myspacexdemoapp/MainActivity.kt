package com.example.myspacexdemoapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.api.SpaceXApi
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val lim: Int = 100
    private var disposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.launches)

        val apolloClient =
            ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
        disposable = CompositeDisposable()
        val button: Button = findViewById(R.id.button)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        val requests = button.clicks()
            .doOnNext {
                button.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            .flatMap {
                SpaceXApi(apolloClient).getLaunches()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                progressBar.visibility = View.INVISIBLE
                textView.visibility = View.VISIBLE
                textView.text = it.data?.launches()?.get(0)?.details()?.take(lim)
            }
            .delay(5, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                progressBar.visibility = View.VISIBLE
                textView.visibility = View.INVISIBLE
                SpaceXApi(apolloClient).getLaunchById("9").map { it2 ->
                    Pair(it, it2)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                progressBar.visibility = View.INVISIBLE
                textView.visibility = View.VISIBLE
                textView.text =
                    it.first.data?.launches()?.get(0)?.details()
                        ?.take(lim) + it.second.data?.launch()?.details()?.take(
                        lim)
            }

        disposable?.add(requests)
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
