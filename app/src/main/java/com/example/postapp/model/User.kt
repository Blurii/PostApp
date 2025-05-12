package com.example.postapp.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val adress: Address,
    val phone: String,
    val website: String,
    val company: Company,
)
