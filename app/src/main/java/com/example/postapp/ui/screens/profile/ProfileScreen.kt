package com.example.postapp.ui.screens.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.postapp.data.saveImageToInternalStorage
import java.io.File
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val imagePath by viewModel.imagePath.collectAsState()

    var firstNameInput by remember(firstName) { mutableStateOf(firstName) }
    var lastNameInput by remember(lastName) { mutableStateOf(lastName) }

    val context = LocalContext.current

    val getImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val savedFileName = saveImageToInternalStorage(context, it)
            viewModel.saveImagePath(savedFileName)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil użytkownika") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Wróć")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imagePath.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(File(context.filesDir, imagePath)),
                    contentDescription = null,
                    modifier = Modifier
                        .size(250.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { getImage.launch("image/*") }) {
                Text("Wybierz zdjęcie")
            }

            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = firstNameInput,
                onValueChange = { firstNameInput = it },
                label = { Text("Imię") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = lastNameInput,
                onValueChange = { lastNameInput = it },
                label = { Text("Nazwisko") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                viewModel.saveUser(firstNameInput, lastNameInput)
            }) {
                Text("Zapisz dane")
            }
            Text(
                text = "Przytrzymaj, by zresetować",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                viewModel.resetProfile()
                                Toast.makeText(context, "Dane zresetowane", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
            )
        }
    }
}
