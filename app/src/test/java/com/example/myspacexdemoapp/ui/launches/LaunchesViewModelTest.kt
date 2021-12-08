package com.example.myspacexdemoapp.ui.launches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.spacexdemoapp.GetLaunchesQuery
import com.example.spacexdemoapp.fragment.MissionDetails
import io.reactivex.rxjava3.core.Observable
import junit.framework.TestCase
import org.hamcrest.core.Is
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LaunchesViewModelTest : TestCase() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    var apolloClient: ApolloClient = mock(ApolloClient::class.java)
    private val spaceXApi = mock(SpaceXApi(apolloClient)::class.java)


    @Mock
    private var parts: MutableList<GetLaunchesQuery.Launch> = mutableListOf()

    private val viewModel by lazy {
        LaunchesViewModel(spaceXApi)
    }

    @Test
    fun `when launch initialized success is retrieved`() {
        val mockResponse: Response<GetLaunchesQuery.Data> =
            mock(Response::class.java) as Response<GetLaunchesQuery.Data>
        val mockData =
            mock(GetLaunchesQuery.Data::class.java, Answers.RETURNS_DEEP_STUBS)
        val mockLaunch =
            mock(GetLaunchesQuery.Launch::class.java,Answers.RETURNS_DEEP_STUBS)
//        val mockFragments =
//            mock(GetLaunchesQuery.Launch.Fragments::class.java,Answers.RETURNS_DEEP_STUBS)
//        val mockMissionDetails =
//            mock(MissionDetails::class.java,Answers.RETURNS_DEEP_STUBS)
//        val mockFragments = GetLaunchesQuery.Launch.Fragments(
//            MissionDetails(
//                "My details",
//                "My date",
//                "My mission name",
//                true
//            )
//        )










//        val mockFragments =
//            mock(GetLaunchesQuery.Launch.Fragments::class.java,Answers.RETURNS_DEEP_STUBS)
//        `when`(mockLaunch.fragments()).thenReturn(mockFragments)
//        `when`(mockLaunch.id()).thenReturn("1111")
//        `when`(mockLaunch.details()).thenReturn("My details")
//
//        `when`(mockFragments.missionDetails().mission_name()).thenReturn("My mission name")
//        `when`(mockFragments.missionDetails().__typename()).thenReturn("My mission name")
//        `when`(mockFragments.missionDetails().launch_date_utc()).thenReturn("My mission name")
//        `when`(mockFragments.missionDetails().launch_success()).thenReturn(true)




//        `when`(mockMissionDetails.__typename()).thenReturn("My typename")
//        `when`(mockMissionDetails.launch_success()).thenReturn(true)
//        `when`(mockMissionDetails.launch_date_utc()).thenReturn("My date")
//        `when`(mockMissionDetails.mission_name()).thenReturn("My mission name")
//        `when`(mockFragments.missionDetails()).thenReturn(mockMissionDetails)
//        `when`(mockLaunch.fragments()).thenReturn(mockFragments)
//        `when`(mockLaunch.__typename()).thenReturn("My typename")
//        `when`(mockLaunch.details()).thenReturn("My details")
//        `when`(mockLaunch.id()).thenReturn("1111")

        parts.add(mockLaunch)



        `when`(mockData.launches()).thenReturn(
            parts
        )

        //doReturn(mylist).`when`(mockData.launches())
        `when`(mockResponse.data).thenReturn(mockData)
        `when`(spaceXApi.getLaunches()).thenReturn(
            Observable.just(
                mockResponse
            )
        )
        val mockObserver = mock(Observer::class.java) as Observer<LaunchesViewState>
        viewModel.launchesLiveData.observeForever(mockObserver)
        viewModel.getLaunches()
        val argumentCaptor = ArgumentCaptor.forClass(LaunchesViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.allValues.last() is LaunchesViewState.Success)
        val actualState = argumentCaptor.allValues.last() as LaunchesViewState.Success
        Assert.assertThat(actualState.model?.get(0)?.number, Is.`is`("1111"))
        Assert.assertThat(actualState.model?.get(0)?.mission?.details, Is.`is`("My details"))
        Assert.assertThat(actualState.model?.get(0)?.mission?.name, Is.`is`("My mission name"))
    }



    private fun getLaunch(): List<GetLaunchesQuery.Launch> {
//        val mockLaunch =
//            mock(GetLaunchesQuery.Launch::class.java)
//        val mockFragments =
//            mock(GetLaunchesQuery.Launch.Fragments::class.java)
//        `when`(mockLaunch.fragments()).thenReturn(mockFragments)
//        `when`(mockLaunch.id()).thenReturn("1111")
//        `when`(mockLaunch.details()).thenReturn("My details")
//        `when`(mockLaunch.fragments().missionDetails().mission_name()).thenReturn("My mission name")

//
//        val mockLaunch =
//            mock(GetLaunchesQuery.Launch::class.java)
//        val mockFragments =                GetLaunchesQuery.Launch.Fragments(
//            MissionDetails(
//                "My details",
//                "My date",
//                "My mission name",
//                true
//            )
//        )
//        `when`(mockLaunch.fragments()).thenReturn(mockFragments)
//        `when`(mockLaunch.id()).thenReturn("1111")
//        `when`(mockLaunch.details()).thenReturn("My details")
//        `when`(mockLaunch.fragments().missionDetails().mission_name()).thenReturn("My mission name")
//
//


//        val mockMissionDetails =
//            mock(MissionDetails::class.java,Answers.RETURNS_DEEP_STUBS)
//
//                `when`(mockMissionDetails.__typename()).thenReturn("My typename")
//                `when`(mockMissionDetails.launch_success()).thenReturn(true)
//                `when`(mockMissionDetails.launch_date_utc()).thenReturn("My typename")
//                `when`(mockMissionDetails.mission_name()).thenReturn("My typename")
//
//
//        val mockLaunchFragments =
//            mock(GetLaunchesQuery.Launch.Fragments::class.java,Answers.RETURNS_DEEP_STUBS)
//
//        `when`(mockLaunchFragments.missionDetails()).thenReturn(mockMissionDetails)

                val mockMissionDetails =
            mock(MissionDetails::class.java,Answers.RETURNS_DEEP_STUBS)
                val mockFragments =
            mock(GetLaunchesQuery.Launch.Fragments::class.java,Answers.RETURNS_DEEP_STUBS)
                val mockLaunch =
            mock(GetLaunchesQuery.Launch::class.java,Answers.RETURNS_DEEP_STUBS)
                        `when`(mockMissionDetails.__typename()).thenReturn("My typename")
                `when`(mockMissionDetails.launch_success()).thenReturn(true)
                `when`(mockMissionDetails.launch_date_utc()).thenReturn("My typename")
                `when`(mockMissionDetails.mission_name()).thenReturn("My typename")
                `when`(mockFragments.missionDetails()).thenReturn(mockMissionDetails)
                `when`(mockLaunch.fragments()).thenReturn(mockFragments)
        `when`(mockLaunch.__typename()).thenReturn("My typename")
        `when`(mockLaunch.details()).thenReturn("My typename")
        `when`(mockMissionDetails.__typename()).thenReturn("My typename")




        return listOf(
            mockLaunch
        )




//        return listOf(
//            GetLaunchesQuery.Launch(
//                "My launch typename",
//                "1111",
//                "My details",
//                null,
//                null,
//                null,
//                GetLaunchesQuery.Launch.Fragments(
//                    MissionDetails(
//                        "My details",
//                        "My date",
//                        "My mission name",
//                        true
//                    )
//                )
//            )
//        )
        //return listOf(mockLaunch)

    }


//
//    @InjectMocks
//    private var spaceXApi: SpaceXApi = SpaceXApi(apolloClient)

//    @Test
//    fun getLaunchesAssertLoading() {
//        val tasksViewModel = mock(LaunchesViewModel(spaceXApi)::class.java)
//        val query = mock(GetLaunchesQuery::class.java)
//        apolloClient.rxQuery(query)
//        spaceXApi.getLaunches()
//        val mockResponse: Response<GetLaunchesQuery.Data> =
//            mock(Response::class.java) as Response<GetLaunchesQuery.Data>
//        val mockData =
//            mock(GetLaunchesQuery.Data::class.java, Answers.RETURNS_DEEP_STUBS)
//
//
//        `when`(mockResponse.data).thenReturn(mockData)
//        `when`(spaceXApi.getLaunches()).thenReturn(
//            Observable.just(
//                mockResponse
//            )
//        )
//
//        //tasksViewModel.getLaunches()
////        val value = tasksViewModel.launchesLiveData.getOrAwaitValue()
////
////        assert(value is LaunchesViewState.Loading)
//
//    }


    @Test
    fun `when getCountries invoked then post default CountryDetailsViewState on success`() {

        val mockResponse: Response<GetLaunchesQuery.Data> =
            mock(Response::class.java) as Response<GetLaunchesQuery.Data>
        val mockData =
            mock(GetLaunchesQuery.Data::class.java, Answers.RETURNS_DEEP_STUBS)
        `when`(mockData.launches()).thenReturn(

            listOf(
                GetLaunchesQuery.Launch(
                    "klkl", "ml", "", null, null, null, GetLaunchesQuery.Launch.Fragments(
                        MissionDetails("", "", "", true)
                    )
                )
            )
        )
//        val mockCountryPreviewFragments= mockCountriesQueryList()
//
        `when`(mockResponse.data).thenReturn(mockData)
//
//        val mockLaunch =
//            mock(GetLaunchesQuery.Launch::class.java)
//
//        `when`(mockData.launches()).thenReturn(
//            mutableListOf(mockLaunch)
//        )

        `when`(spaceXApi.getLaunches()).thenReturn(
            Observable.just(
                mockResponse
            )
        )

        val mockObserver = mock(Observer::class.java) as Observer<LaunchesViewState>
        viewModel.launchesLiveData.observeForever(mockObserver)


        viewModel.getLaunches()

        val argumentCaptor = ArgumentCaptor.forClass(LaunchesViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())

        assert(argumentCaptor.allValues.last() is LaunchesViewState.Success)

        val actualState = argumentCaptor.allValues.last() as LaunchesViewState.Success

//        Assert.assertThat(actualState?.model?.get(0)?.mission?.date, Is.`is`("Test name"))
//        Assert.assertThat(actualState?.model?.get(0)?.mission?.details, Is.`is`("Test capital"))
//        Assert.assertThat(actualState?.model?.get(0)?.mission?.name, Is.`is`("Test subregion name"))
//        Assert.assertThat(
//            actualState?.model?.get(0)?.mission?.rocketName,
//            Is.`is`("https://test.com")
//        )
    }

    private fun mockCountriesQueryList(): List<GetLaunchesQuery.Launch> {
        val mockCountryPreview =
            mock(GetLaunchesQuery.Launch::class.java, Answers.RETURNS_DEEP_STUBS)
//        `when`(mockCountryPreview.details()).thenReturn("Details of this mission is total success")
//        `when`(mockCountryPreview.__typename()).thenReturn("typename was set")
//        mockCountryPreview.apply {
//
//            `when`(
//                mockCountryPreview.fragments().missionDetails().mission_name()
//            ).thenReturn("Mission to mars")
////            `when`(
////                mockCountryPreview.rocket()?.fragments()?.rocketFields()
////                    ?.rocket_name()
////            ).thenReturn("Rocket Musk-1")
//
//        }
        return listOf(mockCountryPreview)
    }


//
//
//    @Test
//    fun getLaunchesAssertSuccess() {
//        val tasksViewModel = LaunchesViewModel(
//            SpaceXApi(
//                ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
//            )
//        )
//        tasksViewModel.getLaunches()
////        val value = tasksViewModel.launchesLiveData.getOrAwaitValue()
////
////        assert(value is LaunchesViewState.Success)
//    }
//
//    @Test
//    fun getLaunchesAssertError() {
//        val tasksViewModel = LaunchesViewModel(
//            SpaceXApi(
//                ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
//            )
//        )
//        tasksViewModel.getLaunches()
////        val value = tasksViewModel.launchesLiveData.getOrAwaitValue()
////
////        assert(value is LaunchesViewState.Error)
//    }
}


