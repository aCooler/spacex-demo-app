package ui.rocketDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.domain.Mission
import com.example.domain.RocketDetailsData
import com.example.domain.RocketDetailsUseCase
import com.example.myspacexdemoapp.ui.rocketDetails.RocketDetailsViewModel
import com.example.myspacexdemoapp.ui.rocketDetails.RocketDetailsViewState
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
class RocketDetailsViewModelTest : TestCase() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val useCase = mock(RocketDetailsUseCase::class.java)
    private val viewModel by lazy {
        RocketDetailsViewModel(getRocketDetailsUseCase = useCase)
    }

    @Test
    fun `when launch by id initialized then success is retrieved`() {
        `when`(useCase.invoke("", "")).thenReturn(
            Flowable.just(
                RocketDetailsData.EMPTY.copy(
                    number = "1111",
                    mission = Mission.EMPTY.copy(
                        details = "My details", name = "My mission name"
                    )
                )
            )
        )

        val mockObserver = mock(Observer::class.java) as Observer<RocketDetailsViewState>
        viewModel.launchLiveData.observeForever(mockObserver)
        viewModel.getLaunch("", "")
        val argumentCaptor = ArgumentCaptor.forClass(RocketDetailsViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.allValues.first() is RocketDetailsViewState.Loading)
        assert(argumentCaptor.allValues.last() is RocketDetailsViewState.Success)
        val actualState = argumentCaptor.allValues.last() as RocketDetailsViewState.Success
        assertEquals(actualState.model.number, "1111")
        assertEquals(actualState.model.mission.details, "My details")
        assertEquals(actualState.model.mission.name, "My mission name")
    }

    @Test
    fun `when launch by id initialized then error is retrieved`() {
        `when`(useCase.invoke("", "")).thenReturn(
            Flowable.error(
                Throwable()
            )
        )
        val mockObserver = mock(Observer::class.java) as Observer<RocketDetailsViewState>
        viewModel.launchLiveData.observeForever(mockObserver)
        viewModel.getLaunch("", "")
        val argumentCaptor = ArgumentCaptor.forClass(RocketDetailsViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.allValues.first() is RocketDetailsViewState.Loading)
        assert(argumentCaptor.allValues.last() is RocketDetailsViewState.Error)
    }
}
