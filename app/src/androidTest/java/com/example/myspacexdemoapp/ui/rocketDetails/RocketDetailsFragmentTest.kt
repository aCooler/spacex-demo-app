package com.example.myspacexdemoapp.ui.rocketDetails

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
import com.example.domain.BasicInfoEntity
import com.example.domain.LinkInfo
import com.example.domain.Mission
import com.example.domain.Payload
import com.example.domain.RocketDetailsData
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.ui.launches.LaunchesViewState
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
class RocketDetailsFragmentTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val viewModel: RocketDetailsViewModel = mockkClass(RocketDetailsViewModel::class)
    private var liveData: MutableLiveData<RocketDetailsViewState> = MutableLiveData()

    @Before
    fun init() {
        launchFragmentInContainer<RocketDetailsFragment>()
        every { viewModel.launchLiveData } returns liveData
    }

    @Test
    fun when_error_retrieved_than_dialog_is_showed() {
        val message = "Test for empty list"
        liveData.postValue(
            RocketDetailsViewState.Error(error = Throwable(message = message))
        )
        onView(ViewMatchers.withText(message))
            .check { _, _ ->
                matches(
                    ViewMatchers.isDisplayed()
                )
            }
    }

    @Test
    fun when_loading_retrieved_than_progress_is_showed() {
        liveData.postValue(
            RocketDetailsViewState.Loading
        )
        onView(withId(R.id.rocket_details_swipe_refresh_details)).check { _, _ ->
            matches(isRefreshing())
        }
    }

    @Test
    fun when_success_retrieved_than_list_is_filled_and_each_item_is_checked() {
        val number = "First Flight"
        liveData.postValue(
            RocketDetailsViewState.Success(
                RocketDetailsData.EMPTY.copy(
                    number = number,
                    basics = BasicInfoEntity.EMPTY,
                    mission = Mission.EMPTY,
                    expandables = emptyList(),
                    payload = Payload.EMPTY,
                    linkInfo = LinkInfo.EMPTY
                )
            )
        )
        onView(withId(R.id.launches_details_list)).check { _, _ ->
            matches(
                atPosition(
                    0,
                    ViewMatchers.hasDescendant(
                        ViewMatchers.withText(number)
                    )
                )
            )
        }
    }

    @Test
    fun when_success_retrieved_than_title_is_checked() {
        val title = "889"
        liveData.postValue(
            RocketDetailsViewState.Success(
                RocketDetailsData.EMPTY.copy(
                    number = "number",
                    basics = BasicInfoEntity.EMPTY,
                    mission = Mission.EMPTY.copy(name = title),
                    expandables = emptyList(),
                    payload = Payload.EMPTY,
                    linkInfo = LinkInfo.EMPTY
                )
            )
        )
        onView(withId(R.id.toolbar)).check { _, _ ->
            matches(
                ViewMatchers.withText(title)
            )
        }
    }

    @Test
    fun when_error_retrieved_than_list_is_empty() {
        LaunchesViewState.Error(Throwable())
        onView(withId(R.id.launches_details_list)).check { view, _ ->
            view as RecyclerView
            assertEquals(view.adapter?.itemCount, 0)
        }
    }

    @Test
    fun when_success_retrieved_than_list_is_checked_for_failed_launch_text_and_color_text() {
        liveData.postValue(
            RocketDetailsViewState.Success(
                RocketDetailsData.EMPTY
            )
        )
        onView(withId(R.id.launches_details_list)).check { _, _ ->
            val text = "inactive"
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

    @Test
    fun when_success_retrieved_than_list_is_checked_for_success_launch_text_and_color_text() {
        liveData.postValue(
            RocketDetailsViewState.Success(
                RocketDetailsData.EMPTY.copy(
                    number = "number",
                    basics = BasicInfoEntity.EMPTY,
                    mission = Mission.EMPTY.copy(success = true),
                    expandables = emptyList(),
                    payload = Payload.EMPTY,
                    linkInfo = LinkInfo.EMPTY
                )
            )
        )
        onView(withId(R.id.launches_details_list)).check { _, _ ->
            val text = "Active"
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
