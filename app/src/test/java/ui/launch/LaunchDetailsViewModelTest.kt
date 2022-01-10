package ui.launch

import androidx.lifecycle.Observer
import com.apollographql.apollo3.api.ApolloResponse
import com.example.domain.GetLaunchDetailsUseCase
import com.example.domain.LaunchData
import com.example.domain.Mission
import com.example.myspacexdemoapp.ui.launch.LaunchDetailsViewModel
import com.example.myspacexdemoapp.ui.launch.LaunchDetailsViewState
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LaunchDetailsViewModelTest : TestCase() {

    private val useCase = mock(GetLaunchDetailsUseCase::class.java)
    private val viewModel by lazy {
        LaunchDetailsViewModel(useCase)
    }

    @Mock
    lateinit var mockObserver: Observer<LaunchDetailsViewState>

    @Test
    fun `when get launches initialized then success is retrieved`() = runTest {
        mock(ApolloResponse::class.java)
        `when`(useCase.invoke("9")).thenReturn(
            flow {
                LaunchData.EMPTY.copy(
                    mission = Mission.EMPTY.copy(
                        rocketName = "AC",
                        details = "My details",
                        name = "My mission name"
                    ),
                )
            }
        )
        launch {
            viewModel.launchLiveData.observeForever(mockObserver)
            viewModel.getLaunch("9")
        }

        val argumentCaptor = ArgumentCaptor.forClass(LaunchDetailsViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.allValues.first() is LaunchDetailsViewState.Loading)
        assert(argumentCaptor.allValues.last() is LaunchDetailsViewState.Success)
        val actualState = argumentCaptor.allValues.last() as LaunchDetailsViewState.Success
        assertEquals(actualState.model.mission.rocketName, "AC")
        assertEquals(actualState.model.mission.details, "My details")
        assertEquals(actualState.model.mission.name, "My mission name")
    }

    @Test
    fun `when get launches initialized then error is retrieved`() = runTest {
        `when`(useCase.invoke("9")).thenReturn(
            flow {
                Throwable()
            }
        )
        launch {
            viewModel.launchLiveData.observeForever(mockObserver)
            viewModel.getLaunch("9")
        }
        val argumentCaptor = ArgumentCaptor.forClass(LaunchDetailsViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.allValues.first() is LaunchDetailsViewState.Loading)
        assert(argumentCaptor.allValues.last() is LaunchDetailsViewState.Error)

    }
}
