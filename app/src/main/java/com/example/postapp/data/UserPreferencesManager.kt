package com.example.postapp.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferencesManager(private val context: Context) {

    companion object {
        val FIRST_NAME_KEY = stringPreferencesKey("first_name")
        val LAST_NAME_KEY = stringPreferencesKey("last_name")
        val IMAGE_PATH_KEY = stringPreferencesKey("image_path")
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    suspend fun saveUserDetails(firstName: String, lastName: String) {
        context.dataStore.edit { prefs ->
            prefs[FIRST_NAME_KEY] = firstName
            prefs[LAST_NAME_KEY] = lastName
        }
    }

    suspend fun saveImagePath(path: String) {
        context.dataStore.edit { prefs ->
            prefs[IMAGE_PATH_KEY] = path
        }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE_KEY] = enabled
        }
    }

    val userFirstName: Flow<String> = context.dataStore.data.map { it[FIRST_NAME_KEY] ?: "" }
    val userLastName: Flow<String> = context.dataStore.data.map { it[LAST_NAME_KEY] ?: "" }
    val imagePath: Flow<String> = context.dataStore.data.map { it[IMAGE_PATH_KEY] ?: "" }
    val darkModeEnabled: Flow<Boolean> = context.dataStore.data.map { it[DARK_MODE_KEY] ?: false }
}
