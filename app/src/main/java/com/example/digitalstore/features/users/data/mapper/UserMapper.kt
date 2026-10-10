package com.example.digitalstore.features.users.data.mapper

import com.example.digitalstore.features.users.data.dto.UserDTO
import com.example.digitalstore.features.users.domain.model.Users

fun UserDTO.toDomainUser(): Users {
    return Users(
        id = id,
        age = age,
        email = email,
        firstName = firstName,
        lastName = lastName,
        image = image
    )
}