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
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
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
            val ob1: Disposable =
                SpaceXApi(apolloClient).getLaunches()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe {
                        button.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { resp ->
                        progressBar.visibility = View.INVISIBLE
                        textView.visibility = View.VISIBLE
                        textView.text = resp.data?.launches?.get(0)?.details?.take(lim)
                        Completable.timer(5, TimeUnit.SECONDS,AndroidSchedulers.mainThread()).subscribe{
                            SpaceXApi(apolloClient).getLaunchById("9")
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe {
                                    progressBar.visibility = View.VISIBLE
                                    textView.visibility = View.INVISIBLE
                                }
                                .subscribe { resp2 ->
                                    progressBar.visibility = View.INVISIBLE
                                    textView.visibility = View.VISIBLE
                                    val text2 = resp2.data?.launch?.details?.take(lim)
                                    textView.text = textView.text.toString() + text2
                                }
                        }
                    }
            disposable?.add(ob1)
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
