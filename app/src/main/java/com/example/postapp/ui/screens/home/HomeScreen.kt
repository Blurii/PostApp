package com.example.postapp.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.postapp.model.Post
import com.example.postapp.model.PostDetails
import com.example.postapp.model.User
import com.example.postapp.model.UserDetails

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigationController: NavController
) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        is HomeViewModel.UiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator()
        }
        is HomeViewModel.UiState.Error -> {
            val msg = (state as HomeViewModel.UiState.Error).message
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text(msg)
            }
        }
        is HomeViewModel.UiState.Success -> {
            val posts = (state as HomeViewModel.UiState.Success).posts
            val users = (state as HomeViewModel.UiState.Success).users.associateBy { it.id }

            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(posts) { post ->
                    val user = users[post.userId]
                    PostItem(post, user, navigationController)
                }
            }
        }
    }
}

@Composable
fun PostItem(post: Post, user: User?, navController: NavController) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
        .clickable { navController.navigate(PostDetails(post.id)) }
    ) {
        Text(post.title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        user?.let {
            Text(
                text = it.name,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.clickable {
                    navController.navigate(UserDetails(it.id))
                }
            )
        }
        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}