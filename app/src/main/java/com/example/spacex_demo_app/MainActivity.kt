package com.example.spacex_demo_app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.example.spacex_demo_app.api.SpaceXApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

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
            it.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            val disposable1 = Observable.zip(
                SpaceXApi(apolloClient).getLaunches().subscribeOn(Schedulers.io()),
                SpaceXApi(apolloClient).getLaunchById("9").subscribeOn(Schedulers.io()),
                {
                        firstResponse: Response<GetLaunchesQuery.Data>,
                        secondResponse: Response<GetLaunchQuery.Data>,
                    ->
                    "${firstResponse.data?.launches?.get(0)?.details?.take(lim)} ${
                        secondResponse.data?.launch?.details?.take(lim)}"
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    progressBar.visibility = View.GONE
                    textView.text = it
                }

            disposable?.add(disposable1)
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
