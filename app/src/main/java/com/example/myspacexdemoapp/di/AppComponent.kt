package com.example.myspacexdemoapp.di

import android.content.Context
import com.example.myspacexdemoapp.ui.launch.DetailsFragment
import com.example.myspacexdemoapp.ui.launches.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ NetModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(detailsFragment: DetailsFragment)
    fun inject(mainFragment: MainFragment)
}
