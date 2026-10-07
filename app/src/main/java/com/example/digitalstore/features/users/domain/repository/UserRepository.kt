package com.example.digitalstore.features.users.domain.repository

import com.example.digitalstore.features.users.domain.model.Users

interface UserRepository {

    suspend fun getUsers(): Result<List<Users>>

    suspend fun getUsersByID(id: Int): Result<Users>
}