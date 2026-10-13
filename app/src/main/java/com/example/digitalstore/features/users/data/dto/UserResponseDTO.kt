package com.example.digitalstore.features.users.data.dto

data class UserResponseDTO(
    val limit: Int,
    val skip: Int,
    val total: Int,
    val users: List<UserDTO>
)