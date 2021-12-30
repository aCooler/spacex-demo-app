package ui.launches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.domain.GetLaunchesUseCase
import com.example.domain.LaunchData
import com.example.domain.Mission
import com.example.myspacexdemoapp.ui.launches.LaunchesViewModel
import com.example.myspacexdemoapp.ui.launches.LaunchesViewState
import io.reactivex.rxjava3.core.Flowable
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LaunchesViewModelTest : TestCase() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val useCase = mock(GetLaunchesUseCase::class.java)
    private val viewModel by lazy {
        LaunchesViewModel(useCase = useCase)
    }

    @Test
    fun `when launch by id initialized then success is retrieved`() {
        `when`(useCase.invoke()).thenReturn(
            Flowable.just(
                listOf(
                    LaunchData.EMPTY.copy(
                        number = "1111",
                        mission = Mission.EMPTY.copy(
                            details = "My details",
                            name = "My mission name"
                        ),
                    )
                )
            )
        )

        val mockObserver = mock(Observer::class.java) as Observer<LaunchesViewState>
        viewModel.launchesLiveData.observeForever(mockObserver)
        viewModel.getLaunches()
        val argumentCaptor = ArgumentCaptor.forClass(LaunchesViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.allValues.first() is LaunchesViewState.Loading)
        assert(argumentCaptor.allValues.last() is LaunchesViewState.Success)
        val actualState = argumentCaptor.allValues.last() as LaunchesViewState.Success
        assertEquals(actualState.model?.get(0)?.number, "1111")
        assertEquals(actualState.model?.get(0)?.mission?.details, "My details")
        assertEquals(actualState.model?.get(0)?.mission?.name, "My mission name")
    }

    @Test
    fun `when launch by id initialized then error is retrieved`() {
        `when`(useCase.invoke()).thenReturn(
            Flowable.error(
                Throwable()
            )
        )
        val mockObserver = mock(Observer::class.java) as Observer<LaunchesViewState>
        viewModel.launchesLiveData.observeForever(mockObserver)
        viewModel.getLaunches()
        val argumentCaptor = ArgumentCaptor.forClass(LaunchesViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.allValues.first() is LaunchesViewState.Loading)
        assert(argumentCaptor.allValues.last() is LaunchesViewState.Error)
    }

}
