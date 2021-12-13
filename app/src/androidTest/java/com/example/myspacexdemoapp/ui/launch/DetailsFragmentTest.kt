package com.example.myspacexdemoapp.ui.launch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.MainActivity
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class DetailsFragmentTest {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @MockK
    lateinit var apolloClient: ApolloClient

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }



    @InjectMockKs
    var spaceXApi = SpaceXApi(apolloClient)
    private val viewModel by lazy {
        LaunchDetailsViewModel(spaceXApi)
    }


    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initValidString() {

    }
//    @Test
//    fun whengetlaunchesinitializedthenerrorisretrieved() {
//
//        every { spaceXApi.getLaunchById("9")} returns Observable.error(Throwable())
//        every { apolloClient.query(null)} returns null
//        val mockObserver = mockk<Observer<LaunchDetailsViewState>>()
//        viewModel.launchLiveData.observeForever(mockObserver)
//        viewModel.getLaunch("9")
//        val argumentCaptor = ArgumentCaptor.forClass(LaunchDetailsViewState::class.java)
//        //Mockito.verify(mockObserver, Mockito.times(2)).onChanged(argumentCaptor.capture())
//        val list = mutableListOf<LaunchDetailsViewState>()
//        verify(exactly = 1) { mockObserver.onChanged(capture(list)) }
//        assert(argumentCaptor.allValues.first() is LaunchDetailsViewState.Loading)
//        assert(argumentCaptor.allValues.last() is LaunchDetailsViewState.Error)
//    }

    @Test
    fun when_data_retrieved_than_list_is_filled() {
        val scenario =launchFragmentInContainer {
            DetailsFragment("9")
        }
        onView(withId(R.id.launches_list)).check(hasItemsCount(0))
    }

    fun hasItemsCount(count: Int): ViewAssertion? {
        return ViewAssertion { view, e ->
            if (view !is RecyclerView) {
                throw e!!
            }
            assertEquals(view.adapter!!.itemCount,count)
        }
    }


    @Test
    fun mainActivityTest() {
                launchFragmentInContainer {
            DetailsFragment("9")
        }
        val yourRecycler = onView(
            allOf(
                instanceOf(RecyclerView::class.java)
            )
        )
        yourRecycler.check { view, noViewFoundException ->
            noViewFoundException?.apply {
                throw this
            }
            assertTrue(view is RecyclerView &&
                    view.adapter != null && view.adapter?.itemCount?:-1 > 0
            )

        }
    }

    @Test
    fun when_data_retrieved_than_alert_dialog_is_showed() {
        launchFragmentInContainer {
            DetailsFragment("9")
        }
    }



//    @Test
//    fun changeText_sameActivity() {
//        launchFragmentInContainer {
//            DetailsFragment("9")
//        }
//        Espresso.onView(
//            AllOf.allOf(
//                ViewMatchers.isAssignableFrom(TextView::class.java),
//                ViewMatchers.withParent(ViewMatchers.isAssignableFrom(Toolbar::class.java))
//            )
//        )
//            .check(ViewAssertions.matches(ViewMatchers.withText("Launch")))


}