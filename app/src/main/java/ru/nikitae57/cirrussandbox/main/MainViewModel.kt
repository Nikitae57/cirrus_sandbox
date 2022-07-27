package ru.nikitae57.cirrussandbox.main

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.nikitae57.cirrussandbox.R
import ru.nikitae57.cirrussandbox.utils.State
import kotlin.random.Random

class MainViewModel(
    private val resources: Resources
) : ViewModel() {
    private val _stateFlow = MutableStateFlow<State<MainStateModel>>(State.Initial)
    val stateFlow: StateFlow<State<MainStateModel>> = _stateFlow

    private val _navigateToSecondActivityEvent = Channel<Boolean>()
    val navigateToSecondActivityEventFlow = _navigateToSecondActivityEvent.receiveAsFlow()

    fun startLoading() = viewModelScope.launch {
        dataFlow
            .flowOn(Dispatchers.Default)
            .onStart { _stateFlow.emit(State.Loading) }
            .catch {
                _stateFlow.emit(
                    State.Error(
                        message = resources.getString(R.string.error_message),
                        retryLabel = resources.getString(R.string.try_again_label),
                        retryAction = ::startLoading
                    )
                )
            }
            .collect { _stateFlow.emit(State.Success(it)) }
    }

    private val dataFlow = flow {
        delay(3000)
        if (Random.nextBoolean()) {
            throw Exception()
        }

        emit(
            MainStateModel(
                labelText = resources.getText(R.string.hello_world),
                buttonText = resources.getString(R.string.open_activity_2),
                buttonAction = { viewModelScope.launch { _navigateToSecondActivityEvent.send(true) } }
            )
        )
    }
}