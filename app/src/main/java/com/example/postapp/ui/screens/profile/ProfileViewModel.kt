package com.example.postapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.postapp.PostApplication
import com.example.postapp.data.UserPreferencesManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(private val preferences: UserPreferencesManager) : ViewModel() {

    val firstName = preferences.userFirstName.stateIn(viewModelScope, SharingStarted.Eagerly, "")
    val lastName = preferences.userLastName.stateIn(viewModelScope, SharingStarted.Eagerly, "")
    val imagePath = preferences.imagePath.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    fun saveUser(first: String, last: String) {
        viewModelScope.launch {
            preferences.saveUserDetails(first, last)
        }
    }

    fun saveImagePath(path: String) {
        viewModelScope.launch {
            preferences.saveImagePath(path)
        }
    }

    fun resetProfile() {
        viewModelScope.launch {
            preferences.saveUserDetails("", "")
            preferences.saveImagePath("")
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PostApplication)
                val preferences = application.container.userPreferencesManager
                ProfileViewModel(preferences)
            }
        }
    }
}
