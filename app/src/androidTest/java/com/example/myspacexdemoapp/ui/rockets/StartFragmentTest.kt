package com.example.myspacexdemoapp.ui.rockets

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.util.TestUtil
import io.mockk.every
import io.mockk.mockkClass
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StartFragmentTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val viewModel: RocketsViewModel = mockkClass(RocketsViewModel::class)
    private val rocketsLiveData: MutableLiveData<RocketsViewState> = MutableLiveData()


    @Before
    fun init() {
        launchFragmentInContainer<RocketsFragment>()
        every { viewModel.rocketsLiveData } returns rocketsLiveData
    }

    @Test
    fun when_loading_retrieved_than_progress_is_showed() {
        rocketsLiveData.postValue(
            RocketsViewState.Loading
        )
        Espresso.onView(ViewMatchers.withId(R.id.swipe_refresh)).check { _, _ ->
            ViewAssertions.matches(TestUtil.isRefreshing())
        }
    }
}