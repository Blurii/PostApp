package com.example.postapp.model

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class PostDetails(val postId: Int)

@Serializable
data class UserDetails(val userId: Int)