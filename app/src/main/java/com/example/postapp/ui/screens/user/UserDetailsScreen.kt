package com.example.postapp.ui.screens.user

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    navigationController: NavController,
    viewModel: UserDetailsViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Szczegóły użytkownika") },
                navigationIcon = {
                    IconButton(onClick = { navigationController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Wróć")
                    }
                }
            )
        }
    ) { padding ->
        when (state) {
            is UserDetailsViewModel.UiState.Loading -> Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is UserDetailsViewModel.UiState.Error -> Text((state as UserDetailsViewModel.UiState.Error).message)

            is UserDetailsViewModel.UiState.Success -> {
                val user = (state as UserDetailsViewModel.UiState.Success).user
                val todos = (state as UserDetailsViewModel.UiState.Success).todos

                LazyColumn(modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)) {
                    item {
                        Text(user.name, style = MaterialTheme.typography.titleLarge)
                        Text("Username: ${user.username}")
                        Text("Email: ${user.email}")
                        Text("Telefon: ${user.phone}")
                        Text("Strona: ${user.website}")
                        Text("Firma: ${user.company.name}")
                        Text("Adres: ${user.address.street}, ${user.address.city} (${user.address.zipcode})")
                        Spacer(modifier = Modifier.height(16.dp))
                        val lat = user.address.geo.lat.toDoubleOrNull() ?: 0.0
                        val lng = user.address.geo.lng.toDoubleOrNull() ?: 0.0
                        val userLocation = LatLng(lat, lng)
                        val cameraPositionState = rememberCameraPositionState {
                            position = CameraPosition.fromLatLngZoom(userLocation, 12f)
                        }
                        GoogleMap(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            cameraPositionState = cameraPositionState
                        ) {
                            Marker(
                                state = MarkerState(position = userLocation),
                                title = user.name,
                                snippet = "${user.address.street}, ${user.address.city}"
                            )
                        }
                        Text("Zadania:", style = MaterialTheme.typography.titleMedium)
                    }
                    items(todos) { todo ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Checkbox(checked = todo.completed, onCheckedChange = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(todo.title)
                        }
                    }
                }
            }
        }
    }
}