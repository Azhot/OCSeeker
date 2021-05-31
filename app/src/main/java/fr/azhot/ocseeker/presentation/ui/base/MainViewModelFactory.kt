package fr.azhot.ocseeker.presentation.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.azhot.ocseeker.network.api.ApiService
import fr.azhot.ocseeker.presentation.ui.main.viewmodel.MainViewModel
import fr.azhot.ocseeker.repository.ContentRepositoryImpl

class MainViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(ContentRepositoryImpl(apiService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}