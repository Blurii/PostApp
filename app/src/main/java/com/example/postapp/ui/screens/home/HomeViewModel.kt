package com.example.postapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.postapp.PostApplication
import com.example.postapp.data.AppRepository
import com.example.postapp.model.Post
import com.example.postapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val appRepository: AppRepository
) : ViewModel() {

    sealed interface UiState {
        object Loading: UiState
        data class Success(val posts: List<Post>, val users: List<User>) : UiState
        data class Error(val message: String) : UiState
    }
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchData()
    }
    private fun fetchData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val posts = appRepository.getPosts()
                val users = appRepository.getUsers()
                _uiState.value = UiState.Success(posts, users)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Błąd: ${e.localizedMessage}")
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PostApplication)
                val appRepository = application.container.repository
                HomeViewModel(appRepository)
            }
        }
    }
}