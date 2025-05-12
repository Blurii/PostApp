package com.example.postapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.postapp.model.Home
import com.example.postapp.ui.screens.home.HomeScreen
import com.example.postapp.ui.screens.home.HomeViewModel
import com.example.postapp.ui.theme.PostAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PostAppTheme {
                val navigationController = rememberNavController()

                NavHost(navController = navigationController, startDestination = Home) {
                    composable<Home> {
                        val viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
                        HomeScreen(
                            viewModel = viewModel,
                            navigationController = navigationController
                        )
                    }

                }
            }
        }
    }
}
