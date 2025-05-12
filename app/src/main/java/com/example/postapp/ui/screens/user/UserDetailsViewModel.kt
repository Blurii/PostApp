package com.example.postapp.ui.screens.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.postapp.PostApplication
import com.example.postapp.data.AppRepository
import com.example.postapp.model.Todo
import com.example.postapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val userId: Int,
    private val appRepository: AppRepository
) : ViewModel() {

    sealed interface UiState {
        object Loading: UiState
        data class Success(val user: User, val todos: List<Todo>) : UiState
        data class Error(val message: String) : UiState
    }
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            try {
                val user = appRepository.getUserById(userId)
                val todos = appRepository.getTodosByUserId(userId)
                _uiState.value = UiState.Success(user, todos)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Błąd: ${e.localizedMessage}")
            }
        }
    }
    companion object {
        fun Factory(userId: Int): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PostApplication)
                val appRepository = application.container.repository
                UserDetailsViewModel(userId, appRepository)
            }
        }
    }
}
