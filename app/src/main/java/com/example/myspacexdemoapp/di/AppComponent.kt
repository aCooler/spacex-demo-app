package com.example.myspacexdemoapp.di

import com.example.myspacexdemoapp.ui.launch.DetailsFragment
import com.example.myspacexdemoapp.ui.launches.MainFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(detailsFragment: DetailsFragment)
    fun injectMain(mainFragment: MainFragment)
}
