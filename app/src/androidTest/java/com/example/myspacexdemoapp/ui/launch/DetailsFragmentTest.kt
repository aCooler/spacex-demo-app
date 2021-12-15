package com.example.myspacexdemoapp.ui.launch

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
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.launches.LaunchUiModel
import com.example.myspacexdemoapp.ui.launches.LinkInfo
import com.example.myspacexdemoapp.ui.launches.Mission
import com.example.myspacexdemoapp.ui.launches.Payload
import com.example.myspacexdemoapp.util.TestUtil.atPosition
import com.example.myspacexdemoapp.util.TestUtil.factoryFor
import com.example.myspacexdemoapp.util.TestUtil.isRefreshing
import com.example.myspacexdemoapp.util.TestUtil.withTextColor
import io.mockk.every
import io.mockk.mockkClass
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailsFragmentTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    var apolloClient: ApolloClient = mockkClass(ApolloClient::class)
    var spaceXApi: SpaceXApi = mockkClass(SpaceXApi(apolloClient)::class)
    var viewModel: LaunchDetailsViewModel = mockkClass(LaunchDetailsViewModel(spaceXApi)::class)

    @Test
    fun when_error_retrieved_than_dialog_is_showed() {
        val detailsFragment = DetailsFragment("9")
        detailsFragment.viewModelFactory = factoryFor(viewModel)
        val liveData: MutableLiveData<LaunchDetailsViewState> = MutableLiveData()
        every { viewModel.launchLiveData } returns liveData
        launchFragmentInContainer {
            detailsFragment
        }.onFragment { fragment ->
            fragment.viewModelFactory = factoryFor(viewModel)
        }
        val message = "Test for empty list"
        liveData.postValue(
            LaunchDetailsViewState.Error(error = Throwable(message = message))
        )
        onView(withText(message)).check { _, _ ->
            matches(ViewMatchers.isDisplayed())
        }
    }

    @Test
    fun when_loading_retrieved_than_progress_is_showed() {
        val detailsFragment = DetailsFragment("9")
        detailsFragment.viewModelFactory = factoryFor(viewModel)
        val liveData: MutableLiveData<LaunchDetailsViewState> = MutableLiveData()
        every { viewModel.launchLiveData } returns liveData
        launchFragmentInContainer {
            detailsFragment
        }.onFragment { fragment ->
            fragment.viewModelFactory = factoryFor(viewModel)
        }
        liveData.postValue(
            LaunchDetailsViewState.Loading
        )
        onView(withId(R.id.swipe_refresh_details)).check { view, e ->
            matches(isRefreshing())
        }
    }

    @Test
    fun when_success_retrieved_than_list_is_filled_and_each_item_is_checked() {
        val detailsFragment = DetailsFragment("9")
        detailsFragment.viewModelFactory = factoryFor(viewModel)
        val liveData: MutableLiveData<LaunchDetailsViewState> = MutableLiveData()
        every { viewModel.launchLiveData } returns liveData
        launchFragmentInContainer {
            detailsFragment
        }.onFragment { fragment ->
            fragment.viewModelFactory = factoryFor(viewModel)
        }
        liveData.postValue(LaunchDetailsViewState.Loading)
        val number = "889"
        liveData.postValue(
            LaunchDetailsViewState.Success(
                model = LaunchUiModel(
                    number,
                    linkInfo = LinkInfo.EMPTY.copy(picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg"),
                    payload = Payload.EMPTY,
                    mission = Mission.EMPTY.copy(name = "Antares")
                )
            )
        )
        onView(withId(R.id.launches_details_list)).check { view, e ->
            view as RecyclerView
            matches(atPosition(0, hasDescendant(withText(number))))
        }
    }

    @Test
    fun when_success_retrieved_than_list_is_checked_for_success_launch_text_and_color_text() {
        val detailsFragment = DetailsFragment("9")
        detailsFragment.viewModelFactory = factoryFor(viewModel)
        val liveData: MutableLiveData<LaunchDetailsViewState> = MutableLiveData()
        every { viewModel.launchLiveData } returns liveData
        launchFragmentInContainer {
            detailsFragment
        }.onFragment { fragment ->
            fragment.viewModelFactory = factoryFor(viewModel)
        }
        liveData.postValue(
            LaunchDetailsViewState.Success(
                model = LaunchUiModel(
                    "889",
                    linkInfo = LinkInfo.EMPTY
                        .copy(picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg"),
                    payload = Payload.EMPTY,
                    mission = Mission.EMPTY
                        .copy(name = "Antares", success = true)
                )
            )
        )
        onView(withId(R.id.launches_details_list)).check { _, _ ->
            val text = "success"
            matches(atPosition(0, hasDescendant(withText(text))))
            matches(atPosition(0, hasDescendant(withTextColor(R.color.success_green))))
        }
    }

    @Test
    fun when_success_retrieved_than_list_is_checked_for_failed_launch_text_and_color_text() {
        val detailsFragment = DetailsFragment("9")
        detailsFragment.viewModelFactory = factoryFor(viewModel)
        val liveData: MutableLiveData<LaunchDetailsViewState> = MutableLiveData()
        every { viewModel.launchLiveData } returns liveData
        launchFragmentInContainer {
            detailsFragment
        }.onFragment { fragment ->
            fragment.viewModelFactory = factoryFor(viewModel)
        }
        liveData.postValue(
            LaunchDetailsViewState.Success(
                model = LaunchUiModel(
                    "889",
                    linkInfo = LinkInfo.EMPTY
                        .copy(picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg"),
                    payload = Payload.EMPTY,
                    mission = Mission.EMPTY
                        .copy(name = "Antares", success = false)
                )
            )
        )
        onView(withId(R.id.launches_details_list)).check { _, _ ->
            val text = "failed"
            matches(atPosition(0, hasDescendant(withText(text))))
            matches(atPosition(0, hasDescendant(withTextColor(R.color.failed_red))))
        }
    }

    @Test
    fun when_success_retrieved_than_title_is_checked() {
        val detailsFragment = DetailsFragment("9")
        detailsFragment.viewModelFactory = factoryFor(viewModel)
        val liveData: MutableLiveData<LaunchDetailsViewState> = MutableLiveData()
        every { viewModel.launchLiveData } returns liveData
        launchFragmentInContainer {
            detailsFragment
        }.onFragment { fragment ->
            fragment.viewModelFactory = factoryFor(viewModel)
        }
        liveData.postValue(
            LaunchDetailsViewState.Success(
                model = LaunchUiModel(
                    "889",
                    linkInfo = LinkInfo.EMPTY,
                    payload = Payload.EMPTY,
                    mission = Mission.EMPTY
                        .copy(name = "Antares")
                )
            )
        )
        onView(withId(R.id.toolbar_details)).check { _, _ ->
            val title = "Antares"
            matches(hasDescendant(withText(title)))
        }
    }

    @Test
    fun when_error_retrieved_than_list_is_empty() {
        val detailsFragment = DetailsFragment("9")
        detailsFragment.viewModelFactory = factoryFor(viewModel)
        val liveData: MutableLiveData<LaunchDetailsViewState> = MutableLiveData()
        every { viewModel.launchLiveData } returns liveData
        launchFragmentInContainer {
            detailsFragment
        }.onFragment { fragment ->
            fragment.viewModelFactory = factoryFor(viewModel)
        }
        liveData.postValue(
            LaunchDetailsViewState.Success(
                model = LaunchUiModel(
                    "889",
                    linkInfo = LinkInfo.EMPTY
                        .copy(picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg"),
                    payload = Payload.EMPTY,
                    mission = Mission.EMPTY
                )
            )
        )
        onView(withId(R.id.launches_details_list)).check { view, _ ->
            view as RecyclerView
            assertEquals(view.adapter?.itemCount, 0)
        }
    }
}
