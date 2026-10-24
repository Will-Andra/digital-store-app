package com.example.digitalstore.features.users.domain.model

data class Users(
    val id: Int,
    val age: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val image: Any
){
    val fullName: String
        get() = "$firstName $lastName"
}
