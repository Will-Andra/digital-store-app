package com.example.digitalstore.features.users.data.remote

import com.example.digitalstore.features.users.data.dto.UserDTO
import com.example.digitalstore.features.users.data.dto.UserResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {

    @GET("users?limit=0")
    suspend fun getUsers(): UserResponseDTO

    @GET("users/{id}")
    suspend fun getUsersbyID(
        @Path("id") id: Int
    ): UserDTO

}