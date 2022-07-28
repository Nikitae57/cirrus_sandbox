package ru.nikitae57.cirrussandbox.main

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.kakaocup.kakao.screen.Screen
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import ru.nikitae57.cirrussandbox.utils.State
import ru.nikitae57.cirrussandbox.utils.TestFragmentActivity

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    private val _stateFlow: MutableStateFlow<State<MainStateModel>> = MutableStateFlow(State.Initial)
    private val _eventFLow = Channel<Boolean>().receiveAsFlow()

    private val viewModel = mockk<MainViewModel>(relaxed = true) {
        every { stateFlow } returns _stateFlow
        every { navigateToSecondActivityEventFlow } returns _eventFLow
    }

    @Test
    fun givenInitialStateWhenThenShouldDisplayEmptyScreen() = runTest {
        launch()
        MainScreen {
            button { isDisplayed() }
            label { isNotDisplayed() }
        }
        _stateFlow.emit(State.Loading)
    }

    @Test
    fun whenActivityCreatedThenShouldCallViewModel() = runTest {
        launch()
        MainScreen { Screen.idle() }
        verify(exactly = 1) { viewModel.startLoading() }
    }

    @Test
    fun givenLoadingStateThenShouldShowOnlyProgressBar() = runTest {
        launch()
        _stateFlow.emit(State.Loading)

        MainScreen {
            progressBar { isDisplayed() }
            label { isNotDisplayed() }
            button { isNotDisplayed() }
        }
    }

    @Test
    fun givenErrorStateThenShouldShowErrorMessage() = runTest {
        launch()
        val state = State.Error(message = "Message", retryLabel = "Retry label") {}
        _stateFlow.emit(state)

        MainScreen {
            progressBar { isNotDisplayed() }
            label { hasText(state.message.toString()) }
            button { hasText(state.retryLabel.toString()) }
        }
    }

    @Test
    fun givenSuccessStateThenShouldShowCorrectContent() = runTest {
        launch()
        val stateModel = MainStateModel(labelText = "label text", buttonText = "button text") {}
        val state = State.Success(stateModel)
        _stateFlow.emit(state)

        MainScreen {
            progressBar { isNotDisplayed() }
            label { hasText(stateModel.labelText.toString()) }
            button { hasText(stateModel.buttonText.toString()) }
        }
    }

    @Test
    fun example() = runTest {
        launch()

        _stateFlow.emit(State.Loading)

        MainScreen {
            progressBar { isDisplayed() }
            label { isNotDisplayed() }
            button { isNotDisplayed() }
        }

        val stateModel = MainStateModel(labelText = "label text", buttonText = "button text") {}
        val state = State.Success(stateModel)
        _stateFlow.emit(state)

        MainScreen {
            progressBar { isNotDisplayed() }
            label { hasText(stateModel.labelText.toString()) }
            button { hasText(stateModel.buttonText.toString()) }
        }
    }

    private fun launch(): ActivityScenario<TestFragmentActivity> {
        val fragment = MainFragment.newTestInstance(mainViewModel = viewModel)
        return launchActivity<TestFragmentActivity>()
            .onActivity {
                it.setFragment(fragment)
            }
    }
}