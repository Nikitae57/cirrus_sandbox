package ru.nikitae57.cirrussandbox.utils

sealed class State<out T> {
    object Initial : State<Nothing>()

    object Loading : State<Nothing>()

    data class Success<T>(val data: T) : State<T>()

    data class Error(
        val message: CharSequence,
        val retryLabel: CharSequence,
        val retryAction: () -> Unit
    ) : State<Nothing>()
}
