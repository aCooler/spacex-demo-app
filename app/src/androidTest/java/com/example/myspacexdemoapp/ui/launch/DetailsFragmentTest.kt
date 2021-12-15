package com.example.myspacexdemoapp.ui.launch

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.launch.DetailsFragmentTest.SwipeRefreshLayoutMatchers.isRefreshing
import com.example.myspacexdemoapp.ui.launches.LaunchUiModel
import com.example.myspacexdemoapp.ui.launches.LinkInfo
import com.example.myspacexdemoapp.ui.launches.Mission
import com.example.myspacexdemoapp.ui.launches.Payload
import com.example.myspacexdemoapp.util.ViewModelUtil
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import junit.framework.TestCase.assertEquals
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    var apolloClient: ApolloClient = mockkClass(ApolloClient::class)
    var spaceXApi: SpaceXApi = mockkClass(SpaceXApi(apolloClient)::class)
    var viewModel: LaunchDetailsViewModel = mockkClass(LaunchDetailsViewModel(spaceXApi)::class)

    @Test
    fun when_error_retrieved_than_dialog_is_showed() {
        val detailsFragment = DetailsFragment("9")
        detailsFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        val liveData: MutableLiveData<LaunchDetailsViewState> = MutableLiveData()
        every { viewModel.launchLiveData } returns liveData
        val list = mutableListOf<LaunchDetailsViewState>()
        val observer = mockk<Observer<LaunchDetailsViewState>>(relaxed = true)
        viewModel.launchLiveData.observeForever(observer)
        launchFragmentInContainer {
            detailsFragment
        }.onFragment { fragment ->
            fragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        }
        liveData.postValue(
            LaunchDetailsViewState.Error(error = Throwable(message = "Test for empty list"))
        )
        verify(exactly = 1) { observer.onChanged(capture(list)) }
        onView(withId(R.id.toolbar_details)).check(matches(ViewMatchers.isDisplayed()))
        val state = list.last() as LaunchDetailsViewState.Error
        onView(withText(state.error.message)).check { view, e ->
            matches(ViewMatchers.isDisplayed())
        }
    }


    fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?> {
        checkNotNull(itemMatcher)
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    @Test
    fun when_loading_retrieved_than_progress_is_showed() {
        val detailsFragment = DetailsFragment("9")
        detailsFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        val liveData: MutableLiveData<LaunchDetailsViewState> = MutableLiveData()
        every { viewModel.launchLiveData } returns liveData
        val list = mutableListOf<LaunchDetailsViewState>()
        val observer = mockk<Observer<LaunchDetailsViewState>>(relaxed = true)
        viewModel.launchLiveData.observeForever(observer)
        launchFragmentInContainer {
            detailsFragment
        }.onFragment { fragment ->
            fragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        }
        liveData.postValue(
            LaunchDetailsViewState.Loading
        )
        verify(exactly = 1) { observer.onChanged(capture(list)) }
        assert(list.last() is LaunchDetailsViewState.Loading)
        onView(withId(R.id.swipe_refresh_details)).check { view, e ->
            matches(isRefreshing())
        }
    }


    object SwipeRefreshLayoutMatchers {
        fun isRefreshing(): Matcher<View> {
            return object : BoundedMatcher<View, SwipeRefreshLayout>(
                SwipeRefreshLayout::class.java
            ) {
                override fun describeTo(description: Description) {
                    description.appendText("is refreshing")
                }

                override fun matchesSafely(view: SwipeRefreshLayout): Boolean {
                    return view.isRefreshing
                }
            }
        }
    }


    fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return constraints
            }

            override fun getDescription(): String {
                return action.description
            }

            override fun perform(uiController: UiController?, view: View?) {
                action.perform(uiController, view)
            }
        }
    }

    @Test
    fun when_success_retrieved_than_list_is_filled_and_each_item_is_checked() {
        val detailsFragment = DetailsFragment("9")
        detailsFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        val liveData: MutableLiveData<LaunchDetailsViewState> = MutableLiveData()
        every { viewModel.launchLiveData } returns liveData
        val list = mutableListOf<LaunchDetailsViewState>()
        val observer = mockk<Observer<LaunchDetailsViewState>>(relaxed = true)
        viewModel.launchLiveData.observeForever(observer)
        launchFragmentInContainer {
            detailsFragment
        }.onFragment { fragment ->
            fragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        }
        liveData.postValue(LaunchDetailsViewState.Loading)
        liveData.postValue(
            LaunchDetailsViewState.Success(
                model = LaunchUiModel(
                    "889",
                    linkInfo = LinkInfo(
                        badge = LinkInfo.EMPTY.badge,
                        picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg",
                        video = LinkInfo.EMPTY.video,
                        pictures = emptyList()
                    ),
                    payload = Payload.EMPTY,
                    mission = Mission.EMPTY.copy(name = "Antares")
                )
            )
        )
        onView(withId(R.id.launches_details_list)).check(matches(ViewMatchers.isDisplayed()))
        verify(exactly = 2) { observer.onChanged(capture(list)) }
        val state = list.last() as LaunchDetailsViewState.Success
        onView(withId(R.id.launches_details_list)).check { view, e ->
            if (view !is RecyclerView) {
                throw e!!
            }

            matches(atPosition(0, hasDescendant(withText(state.model.number))))

        }
        val title = state.model.mission.name
        onView(withText(title)).check(matches(ViewMatchers.isDisplayed()))

    }


    @Test
    fun when_success_retrieved_than_title_is_checked() {
        val detailsFragment = DetailsFragment("9")
        detailsFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        val liveData: MutableLiveData<LaunchDetailsViewState> = MutableLiveData()
        every { viewModel.launchLiveData } returns liveData
        val list = mutableListOf<LaunchDetailsViewState>()
        val observer = mockk<Observer<LaunchDetailsViewState>>(relaxed = true)
        viewModel.launchLiveData.observeForever(observer)
        launchFragmentInContainer {
            detailsFragment
        }.onFragment { fragment ->
            fragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        }
        liveData.postValue(
            LaunchDetailsViewState.Success(
                model = LaunchUiModel(
                    "889",
                    linkInfo = LinkInfo.EMPTY,
                    payload = Payload.EMPTY,
                    mission = Mission.EMPTY.copy(name = "Antares")
                )
            )
        )
        verify(exactly = 1) { observer.onChanged(capture(list)) }
        val state = list.last() as LaunchDetailsViewState.Success
        onView(withId(R.id.toolbar_details)).check { view, e ->
            if (view !is Toolbar) {
                throw e!!
            }
            val title = state.model.mission.name
            matches(withText(title))
            matches(hasDescendant(withText(title)))
        }
    }

    @Test
    fun when_error_retrieved_than_list_is_empty() {
        val detailsFragment = DetailsFragment("9")
        detailsFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        every { spaceXApi.getLaunchById("9") } returns Observable.error(Throwable())
        val liveData: MutableLiveData<LaunchDetailsViewState> = MutableLiveData()
        every { viewModel.launchLiveData } returns liveData
        val list = mutableListOf<LaunchDetailsViewState>()
        val observer = mockk<Observer<LaunchDetailsViewState>>(relaxed = true)
        viewModel.launchLiveData.observeForever(observer)
        launchFragmentInContainer {
            detailsFragment
        }.onFragment { fragment ->
            fragment.viewModelFactory = ViewModelUtil.createFor(viewModel)

        }
        liveData.postValue(LaunchDetailsViewState.Loading)
        liveData.postValue(
            LaunchDetailsViewState.Success(
                model = LaunchUiModel(
                    "889",
                    linkInfo = LinkInfo(
                        badge = LinkInfo.EMPTY.badge,
                        picture = "https://farm5.staticflickr.com/4477/38056454431_a5f40f9fd7_o.jpg",
                        video = LinkInfo.EMPTY.video,
                        pictures = emptyList()
                    ),
                    payload = Payload.EMPTY,
                    mission = Mission.EMPTY
                )
            )
        )
        onView(withId(R.id.launches_details_list)).check(matches(ViewMatchers.isDisplayed()))
        verify(exactly = 2) { observer.onChanged(capture(list)) }
        assert(list.last() is LaunchDetailsViewState.Success)
        onView(withId(R.id.launches_details_list)).check { view, e ->
            if (view !is RecyclerView) {
                throw e!!
            }
            assertEquals(view.adapter?.itemCount, 0)
        }
        assert(list.last() is LaunchDetailsViewState.Success)
    }
}