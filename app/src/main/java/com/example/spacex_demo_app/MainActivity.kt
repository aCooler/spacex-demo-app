package com.example.spacex_demo_app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloClient
import com.example.spacex_demo_app.api.SpaceXApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
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
        button.setOnClickListener {
            val ob0: Disposable =
                SpaceXApi(apolloClient).getLaunches()
                    .doOnSubscribe {
                        button.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .map {
                        progressBar.visibility = View.INVISIBLE
                        textView.visibility = View.VISIBLE
                        textView.text = it.data?.launches?.get(0)?.details?.take(lim)
                        it
                    }
                    .delay(5, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap { it ->
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
                            it.first.data?.launches?.get(0)?.details?.take(lim) + it.second.data?.launch?.details?.take(
                                lim)
                    }
            disposable?.add(ob0)
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
