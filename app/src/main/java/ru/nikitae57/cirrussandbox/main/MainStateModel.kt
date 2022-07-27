package ru.nikitae57.cirrussandbox.main

data class MainStateModel(
    val labelText: CharSequence,
    val buttonText: CharSequence,
    val buttonAction: () -> Unit
)