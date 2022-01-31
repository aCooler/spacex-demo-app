package com.example.myspacexdemoapp.ui.launches

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.domain.LaunchData
import com.example.domain.LinkInfo
import com.example.domain.Mission
import com.example.domain.Payload
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.util.TestUtil.atPosition
import com.example.myspacexdemoapp.util.TestUtil.isRefreshing
import com.example.myspacexdemoapp.util.TestUtil.withTextColor
import io.mockk.every
import io.mockk.mockkClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainFragmentTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val viewModel: LaunchesViewModel = mockkClass(LaunchesViewModel::class)
    private var liveData: MutableLiveData<LaunchesViewState> = MutableLiveData()

    @Before
    fun init() {
        launchFragmentInContainer<MainFragment>(
            fragmentArgs = Bundle().apply {
                putBoolean("isNotTest",
                    false)
            })
        every { viewModel.launchesLiveData } returns liveData
    }

    @Test
    fun when_error_retrieved_than_dialog_is_showed() {
        val message = "Test for empty list"
        liveData.postValue(
            LaunchesViewState.Error(error = Throwable(message = message))
        )
        onView(withId(R.id.toolbar))
            .check(matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText(message)).check { _, _ ->
            matches(ViewMatchers.isDisplayed())
        }
    }

    @Test
    fun when_loading_retrieved_than_progress_is_showed() {
        liveData.postValue(
            LaunchesViewState.Loading
        )
        onView(withId(R.id.swipe_refresh_details)).check { _, _ ->
            matches(isRefreshing())
        }
    }

    @Test
    fun when_success_retrieved_than_list_is_filled_and_each_item_is_checked() {
        val number = "889"
        liveData.postValue(
            LaunchesViewState.Success(
                listOf(
                    LaunchData(
                        number,
                        linkInfo = LinkInfo.EMPTY
                            .copy(picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg"),
                        payload = Payload.EMPTY,
                        mission = Mission.EMPTY
                            .copy(name = "Antares")
                    )
                )
            )
        )
        onView(withId(R.id.launches_list)).check { _, _ ->
            matches(
                atPosition(
                    0,
                    ViewMatchers.hasDescendant(ViewMatchers.withText(number))
                )
            )
        }
    }

    @Test
    fun when_success_retrieved_than_title_is_checked() {
        LaunchesViewState.Success(
            listOf(
                LaunchData(
                    "889",
                    linkInfo = LinkInfo.EMPTY
                        .copy(picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg"),
                    payload = Payload.EMPTY,
                    mission = Mission.EMPTY
                )
            )
        )
        onView(withId(R.id.toolbar)).check { _, _ ->
            val title = "Launches"
            matches(ViewMatchers.withText(title))
        }
    }

    @Test
    fun when_error_retrieved_than_list_is_empty() {
        LaunchesViewState.Error(Throwable())
        onView(withId(R.id.launches_list)).check { view, _ ->
            view as RecyclerView
            assertEquals(view.adapter?.itemCount, 0)
        }
    }

    @Test
    fun when_success_retrieved_than_list_is_checked_for_failed_launch_text_and_color_text() {
        liveData.postValue(
            LaunchesViewState.Success(
                listOf(
                    LaunchData(
                        "889",
                        linkInfo = LinkInfo.EMPTY
                            .copy(picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg"),
                        payload = Payload.EMPTY,
                        mission = Mission.EMPTY
                            .copy(name = "Antares", success = false)
                    )
                )
            )
        )
        onView(withId(R.id.launches_details_list)).check { _, _ ->
            val text = "failed"
            matches(
                atPosition(
                    0,
                    ViewMatchers.hasDescendant(ViewMatchers.withText(text))
                )
            )
            matches(
                atPosition(
                    0,
                    ViewMatchers.hasDescendant(withTextColor(R.color.failed_red))
                )
            )
        }
    }

    fun when_success_retrieved_than_list_is_checked_for_success_launch_text_and_color_text() {
        liveData.postValue(
            LaunchesViewState.Success(
                listOf(
                    LaunchData(
                        "889",
                        linkInfo = LinkInfo.EMPTY
                            .copy(picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg"),
                        payload = Payload.EMPTY,
                        mission = Mission.EMPTY
                            .copy(name = "Antares", success = true)
                    )
                )
            )
        )
        onView(withId(R.id.launches_details_list)).check { _, _ ->
            val text = "success"
            matches(
                atPosition(
                    0,
                    ViewMatchers.hasDescendant(ViewMatchers.withText(text))
                )
            )
            matches(
                atPosition(
                    0,
                    ViewMatchers.hasDescendant(withTextColor(R.color.success_green))
                )
            )
        }
    }
}
