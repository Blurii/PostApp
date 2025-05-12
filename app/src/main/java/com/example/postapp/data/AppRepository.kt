package com.example.postapp.data

import com.example.postapp.data.network.ApiService
import com.example.postapp.model.Post
import com.example.postapp.model.Todo
import com.example.postapp.model.User

class AppRepository(private val api: ApiService) {

    suspend fun getPosts(): List<Post> = api.getPosts()

    suspend fun getUsers(): List<User> = api.getUsers()

    suspend fun getPostById(postId: Int): Post = api.getPostById(postId)

    suspend fun getUserById(userId: Int): User = api.getUserById(userId)

    suspend fun getTodosByUserId(userId: Int): List<Todo> = api.getTodosByUserId(userId)
}