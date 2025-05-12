package com.example.postapp

import android.app.Application
import com.example.postapp.data.AppContainer
import com.example.postapp.data.DefaultAppContainer

class PostApplication : Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
    }
}