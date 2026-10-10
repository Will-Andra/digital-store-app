package com.example.digitalstore.features.users.data.repository

import com.example.digitalstore.core.util.safeCall
import com.example.digitalstore.features.users.data.mapper.toDomainUser
import com.example.digitalstore.features.users.data.remote.UserAPI
import com.example.digitalstore.features.users.domain.model.Users
import com.example.digitalstore.features.users.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userAPI: UserAPI
): UserRepository {

    override suspend fun getUsers(): Result<List<Users>> {
        return safeCall {
            val response = userAPI.getUsers()
            response.userDTOS.map { user ->
                user.toDomainUser()
            }
        }
    }

    override suspend fun getUsersByID(id: Int): Result<Users> {
        return safeCall {
            val userDto = userAPI.getUsersbyID(id)
            userDto.toDomainUser()
        }
    }
}