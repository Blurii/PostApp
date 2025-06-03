package com.example.postapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.postapp.PostApplication
import com.example.postapp.data.UserPreferencesManager
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferences: UserPreferencesManager
) : ViewModel() {

    val darkMode: StateFlow<Boolean> = preferences.darkModeEnabled
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleDarkMode() {
        viewModelScope.launch {
            preferences.setDarkMode(!darkMode.value)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PostApplication)
                val preferences = application.container.userPreferencesManager
                SettingsViewModel(preferences)
            }
        }
    }
}