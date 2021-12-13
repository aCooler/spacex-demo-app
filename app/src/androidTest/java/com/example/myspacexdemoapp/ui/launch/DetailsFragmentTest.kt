package com.example.myspacexdemoapp.ui.launch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.SpaceXApi
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNotNull
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


    @Before
    fun initValidString() {}

    @Test
    fun when_error_retrieved_than_dialog_is_showed() {
        //TODO state from livedata
        launchFragmentInContainer {
            DetailsFragment("9")
        }.onFragment {
            assertNotNull(it.alertDialog)
            assertNotNull(it.alertDialog)
            assert(it.alertDialog.isShowing)
            it.alertDialog.dismiss()
            it.parentFragmentManager.executePendingTransactions()
            assertNotNull(it.alertDialog)
        }
    }


    @Test
    fun when_success_retrieved_than_list_is_filled() {
        //TODO state from livedata
        launchFragmentInContainer {
            DetailsFragment("9")
        }.onFragment { fragment ->
            onView(withId(R.id.launches_list)).check { view, e ->
                if (view !is RecyclerView) {
                    throw e!!
                }
                assertEquals(view.adapter?.itemCount, 0)
            }
        }
    }
}