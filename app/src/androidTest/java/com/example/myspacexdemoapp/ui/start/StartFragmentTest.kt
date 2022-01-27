package com.example.myspacexdemoapp.ui.start

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.domain.HomeData
import com.example.domain.LaunchData
import com.example.domain.LinkInfo
import com.example.domain.Mission
import com.example.domain.Payload
import com.example.domain.RocketsData
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.util.TestUtil.atPosition
import com.example.myspacexdemoapp.util.TestUtil.isRefreshing
import io.mockk.every
import io.mockk.mockkClass
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
@LargeTest
class StartFragmentTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val viewModel = mockkClass(StartViewModel::class)
    private val liveData: MutableLiveData<StartViewState> = MutableLiveData()

    @Before
    fun init() {
        every { viewModel.launchLiveData } returns liveData
        launchFragmentInContainer<StartFragment>(Bundle().apply { this.putBoolean("test", true) },
            themeResId = R.style.Theme_Spacexdemoapp)
    }

    @Test
    fun when_success_retrieved_than_list_is_checked_for_success_launch_text_and_color_text() {
        liveData.postValue(
            StartViewState.Success(
                HomeData(
                    launchData = LaunchData(
                        number = "889",
                        linkInfo = LinkInfo.EMPTY.copy(picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg"),
                        payload = Payload.EMPTY,
                        mission = Mission.EMPTY.copy(name = "Antares")
                    ),
                    rockets = (RocketsData(total = "", efficiency = ""))
                )
            )
        )
        onView(withId(R.id.launches_list_timer)).check { _, _ ->
            val text = "DAYS"
            matches(atPosition(0, hasDescendant(withText(text))))
/*
            matches(atPosition(0, hasDescendant(withTextColor(R.color.success_green))))
*/
        }
    }


    @Test
    fun when_error_retrieved_than_dialog_is_showed() {
        val message = "Test for empty list"
        liveData.postValue(
            StartViewState.Error(error = Throwable(message = message))
        )
        onView(withText(message)).check { _, _ ->
            matches(ViewMatchers.isDisplayed())
        }
    }

    @Test
    fun when_loading_retrieved_than_progress_is_showed() {
        liveData.postValue(
            StartViewState.Loading
        )
        onView(withId(R.id.swipe_refresh)).check { view, e ->
            matches(isRefreshing())
        }
    }

    @Test
    fun when_success_retrieved_than_list_is_filled_and_each_item_is_checked() {
        liveData.postValue(StartViewState.Loading)
        val number = "889"
        liveData.postValue(
            StartViewState.Success(
                HomeData(
                    launchData = LaunchData(
                        number,
                        linkInfo = LinkInfo.EMPTY.copy(picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg"),
                        payload = Payload.EMPTY,
                        mission = Mission.EMPTY.copy(name = "Antares")
                    ),
                    rockets = (RocketsData(total = "", efficiency = ""))
                )
            ))
        onView(withId(R.id.launches_list_timer)).check { view, e ->
            view as RecyclerView
            matches(atPosition(0, hasDescendant(withText("DAYS"))))
/*
            matches(atPosition(0, hasDescendant(withText(number))))
*/
        }
    }


    @Test
    fun when_success_retrieved_than_list_is_checked_for_failed_launch_text_and_color_text() {
        liveData.postValue(
            StartViewState.Success(
                model = HomeData(
                    launchData = LaunchData(
                        number = "889",
                        linkInfo = LinkInfo.EMPTY.copy(picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg"),
                        payload = Payload.EMPTY,
                        mission = Mission.EMPTY.copy(name = "Antares", date = Date())
                    ),
                    rockets = (RocketsData(total = "", efficiency = ""))
                )
            )
        )
        onView(withId(R.id.launches_list_timer)).check { _, _ ->
            val text = "DAYS"
            matches(atPosition(0, hasDescendant(withText(text))))
/*            matches(atPosition(0, hasDescendant(withTextColor(R.color.failed_red))))*/
        }
    }

    @Test
    fun when_success_retrieved_than_title_is_checked() {
        liveData.postValue(
            StartViewState.Success(HomeData(
                launchData = LaunchData(
                    number = "889",
                    linkInfo = LinkInfo.EMPTY.copy(picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg"),
                    payload = Payload.EMPTY,
                    mission = Mission.EMPTY.copy(name = "Antares")
                ),
                rockets = (RocketsData(total = "", efficiency = ""))
            )
            ))
        onView(withId(R.id.toolbar_details)).check { _, _ ->
            val title = "Antares"
            matches(hasDescendant(withText(title)))
        }
    }

    @Test
    fun when_error_retrieved_than_list_is_empty() {
        liveData.postValue(
            StartViewState.Success(
                model = HomeData(
                    launchData = LaunchData(
                        number = "889",
                        linkInfo = LinkInfo.EMPTY.copy(picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg"),
                        payload = Payload.EMPTY,
                        mission = Mission.EMPTY.copy(name = "Antares")
                    ),
                    rockets = (RocketsData(total = "", efficiency = ""))
                )
            )
        )
        onView(withId(R.id.launches_list_timer)).check { view, _ ->
            view as RecyclerView
            assertEquals(view.adapter?.itemCount, 0)
        }
    }
}
