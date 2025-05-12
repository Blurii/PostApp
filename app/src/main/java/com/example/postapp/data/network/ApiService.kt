package com.example.postapp.data.network

import com.example.postapp.model.Post
import com.example.postapp.model.Todo
import com.example.postapp.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): User

    @GET("todos")
    suspend fun getTodosByUserId(@Query("userId") userId: Int): List<Todo>
}