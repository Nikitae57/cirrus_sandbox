package ru.nikitae57.cirrussandbox

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.nikitae57.cirrussandbox.main.MainViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelsFactory(private val resources: Resources) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> MainViewModel(resources) as T
            else -> super.create(modelClass)
        }
    }
}