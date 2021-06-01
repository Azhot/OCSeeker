package fr.azhot.ocseeker.presentation.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.azhot.ocseeker.domain.model.Content
import fr.azhot.ocseeker.network.util.Resource
import fr.azhot.ocseeker.repository.ContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ContentRepository) : ViewModel() {

    private val _contents = MutableLiveData<Resource<List<Content>?>>()
    val contents: LiveData<Resource<List<Content>?>>
        get() = _contents

    fun getContents(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _contents.postValue(Resource.loading(data = null))
            try {
                _contents.postValue(Resource.success(repository.getContents(title)))
            } catch (e: Exception) {
                _contents.postValue(
                    Resource.error(
                        data = null,
                        message = e.message ?: "An error has occurred !"
                    )
                )
            }
        }
    }
}