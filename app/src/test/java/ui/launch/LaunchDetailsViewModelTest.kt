package ui.launch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.apollographql.apollo3.api.ApolloResponse
import com.example.domain.GetLaunchDetailsUseCase
import com.example.domain.LaunchData
import com.example.domain.Mission
import com.example.myspacexdemoapp.ui.launch.LaunchDetailsViewModel
import com.example.myspacexdemoapp.ui.launch.LaunchDetailsViewState
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
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

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private val useCase = mock(GetLaunchDetailsUseCase::class.java)
    private val viewModel by lazy {
        LaunchDetailsViewModel(useCase)
    }

    @Mock
    lateinit var mockObserver: Observer<LaunchDetailsViewState>

    @Test
    suspend fun `when get launches initialized then success is retrieved`() {
        coroutineScope.runBlockingTest {
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
            viewModel.launchLiveData.observeForever(mockObserver)
            viewModel.getLaunch("9")
            val argumentCaptor = ArgumentCaptor.forClass(LaunchDetailsViewState::class.java)
            verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
            assert(argumentCaptor.allValues.first() is LaunchDetailsViewState.Loading)
            assert(argumentCaptor.allValues.last() is LaunchDetailsViewState.Success)
            val actualState = argumentCaptor.allValues.last() as LaunchDetailsViewState.Success
            assertEquals(actualState.model.mission.rocketName, "AC")
            assertEquals(actualState.model.mission.details, "My details")
            assertEquals(actualState.model.mission.name, "My mission name")
        }
    }

    @Test
    suspend fun `when get launches initialized then error is retrieved`() {
        `when`(useCase.invoke("9")).thenReturn(
            flow {
                Throwable()
            }
        )
        viewModel.launchLiveData.observeForever(mockObserver)

        viewModel.getLaunch("9")


        val argumentCaptor = ArgumentCaptor.forClass(LaunchDetailsViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.allValues.first() is LaunchDetailsViewState.Loading)
        assert(argumentCaptor.allValues.last() is LaunchDetailsViewState.Error)
    }

    @Test
    fun `runBlockingTest() auto-advances virtual time`() = coroutineScope.runBlockingTest {
        `when`(useCase.invoke("9")).thenReturn(
            flow {
                Throwable()
            }
        )
        viewModel.launchLiveData.observeForever(mockObserver)
        viewModel.getLaunch("9")


        val argumentCaptor = ArgumentCaptor.forClass(LaunchDetailsViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.allValues.first() is LaunchDetailsViewState.Loading)
        assert(argumentCaptor.allValues.last() is LaunchDetailsViewState.Error)
    }
}

@ExperimentalCoroutinesApi
class MainCoroutineScopeRule(val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    TestWatcher(),
    TestCoroutineScope by TestCoroutineScope(dispatcher) {
    override fun starting(description: Description?) {
        super.starting(description)
        // If your codebase allows the injection of other dispatchers like
        // Dispatchers.Default and Dispatchers.IO, consider injecting all of them here
        // and renaming this class to `CoroutineScopeRule`
        //
        // All injected dispatchers in a test should point to a single instance of
        // TestCoroutineDispatcher.
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}