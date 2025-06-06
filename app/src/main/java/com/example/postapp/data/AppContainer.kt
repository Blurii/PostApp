package com.example.postapp.data

import android.content.Context
import com.example.postapp.data.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val repository: AppRepository
    val userPreferencesManager: UserPreferencesManager
}

class DefaultAppContainer(context: Context) : AppContainer {

    private val baseUrl = "https://jsonplaceholder.typicode.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val repository: AppRepository by lazy {
        AppRepository(apiService)
    }
    override val userPreferencesManager: UserPreferencesManager by lazy {
        UserPreferencesManager(context)
    }

}