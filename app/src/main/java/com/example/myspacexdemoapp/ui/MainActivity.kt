package com.example.myspacexdemoapp.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.ui.launch.DetailsFragment
import com.example.myspacexdemoapp.ui.launches.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var activitiesViewModel: ActivitiesManagerViewModel
    private lateinit var activitiesManagerViewModelFactory: ActivitiesManagerViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        title = getString(R.string.title)

        activitiesManagerViewModelFactory = ActivitiesManagerViewModelFactory()
        activitiesViewModel = ViewModelProvider(
            this,
            activitiesManagerViewModelFactory
        ).get(ActivitiesManagerViewModel::class.java)

        activitiesViewModel.launchesLiveData.observe(this, { state ->
            when (state) {
                is ActivitiesManagerViewState.Details -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        val detailsFragment: Fragment = DetailsFragment(state.id)
                        addToBackStack("details_fragment")
                        replace(R.id.fragment_container, detailsFragment)
                    }
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                ActivitiesManagerViewState.Main -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        val mainFragment = MainFragment()
                        addToBackStack("main_fragment")
                        replace(R.id.fragment_container, mainFragment)
                    }
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                is ActivitiesManagerViewState.Error -> TODO()
            }
        })

        activitiesViewModel.startMainFragment()

    }


}
