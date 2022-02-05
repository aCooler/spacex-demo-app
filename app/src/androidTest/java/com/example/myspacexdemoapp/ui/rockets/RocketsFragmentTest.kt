package com.example.myspacexdemoapp.ui.rockets

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.domain.RocketData
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.ui.launch.DetailsFragment
import com.example.myspacexdemoapp.util.TestUtil
import io.mockk.every
import io.mockk.mockkClass
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RocketsFragmentTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val viewModel = mockkClass(RocketsViewModel::class)
    private val liveData: MutableLiveData<RocketsViewState> = MutableLiveData()

    @Before
    fun init() {
        every { viewModel.rocketsLiveData } returns liveData
        launchFragmentInContainer<DetailsFragment>()
    }

    @Test
    fun when_error_retrieved_than_dialog_is_showed() {
        val message = "Test for empty list"
        liveData.postValue(
            RocketsViewState.Error(
                error = Throwable(message = message)
            )
        )
        Espresso.onView(ViewMatchers.withText(message)).check { _, _ ->
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        }
    }

    @Test
    fun when_loading_retrieved_than_progress_is_showed() {
        liveData.postValue(
            RocketsViewState.Loading
        )
        Espresso.onView(ViewMatchers.withId(R.id.swipe_refresh_details)).check { view, e ->
            ViewAssertions.matches(TestUtil.isRefreshing())
        }
    }

    @Test
    fun when_success_retrieved_than_list_is_filled_and_each_item_is_checked() {
        liveData.postValue(RocketsViewState.Loading)
        val number = "889"
        liveData.postValue(
            RocketsViewState.Success(
                model = mutableListOf(
                    RocketData.EMPTY
                )
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.launches_details_list)).check { view, e ->
            view as RecyclerView
            ViewAssertions.matches(
                TestUtil.atPosition(
                    0,
                    ViewMatchers.hasDescendant(
                        ViewMatchers.withText(number)
                    )
                )
            )
        }
    }

    @Test
    fun when_success_retrieved_than_list_is_checked_for_success_launch_text_and_color_text() {
        liveData.postValue(
            RocketsViewState.Success(
                model = mutableListOf(RocketData.EMPTY)
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.launches_details_list)).check { _, _ ->
            val text = "success"
            ViewAssertions.matches(
                TestUtil.atPosition(
                    0,
                    ViewMatchers.hasDescendant(
                        ViewMatchers.withText(text)
                    )
                )
            )
            ViewAssertions.matches(
                TestUtil.atPosition(0,
                    ViewMatchers.hasDescendant(
                    TestUtil.withTextColor(R.color.success_green)
                )
                )
            )
        }
    }

    @Test
    fun when_success_retrieved_than_list_is_checked_for_failed_launch_text_and_color_text() {
        liveData.postValue(
            RocketsViewState.Success(
                model = mutableListOf(
                    RocketData.EMPTY
                )
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.launches_details_list)).check { _, _ ->
            val text = "failed"
            ViewAssertions.matches(
                TestUtil.atPosition(
                    0,
                    ViewMatchers.hasDescendant(
                        ViewMatchers.withText(text)
                    )
                )
            )
            ViewAssertions.matches(
                TestUtil.atPosition(
                    0,
                    ViewMatchers.hasDescendant(
                        TestUtil.withTextColor(R.color.failed_red)
                    )
                )
            )
        }
    }

    @Test
    fun when_success_retrieved_than_title_is_checked() {
        liveData.postValue(
            RocketsViewState.Success(
                model = mutableListOf(
                    RocketData.EMPTY
                )
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.toolbar_details)).check { _, _ ->
            val title = "Antares"
            ViewAssertions.matches(
                ViewMatchers.hasDescendant(
                    ViewMatchers.withText(title)
                )
            )
        }
    }

    @Test
    fun when_error_retrieved_than_list_is_empty() {
        liveData.postValue(
            RocketsViewState.Success(
                model = mutableListOf(
                    RocketData.EMPTY
                )
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.launches_details_list)).check { view, _ ->
            view as RecyclerView
            TestCase.assertEquals(
                view.adapter?.itemCount, 0
            )
        }
    }
}
