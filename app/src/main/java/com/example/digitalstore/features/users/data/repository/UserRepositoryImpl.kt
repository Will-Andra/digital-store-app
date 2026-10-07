package com.example.digitalstore.features.users.data.repository

import com.example.digitalstore.features.users.data.mapper.toDomainUser
import com.example.digitalstore.features.users.data.remote.UserAPI
import com.example.digitalstore.features.users.domain.model.Users
import com.example.digitalstore.features.users.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userAPI: UserAPI
): UserRepository {

    override suspend fun getUsers(): Result<List<Users>> {
        return try {
            val response = userAPI.getUsers()
            val domainUsers = response.userDTOS.map {user->
                user.toDomainUser()
            }
            Result.success(domainUsers)

        }catch (error: Exception){
            Result.failure(error)
        }
    }

    override suspend fun getUsersByID(id: Int): Result<Users> {
        return try {
            val userDto = userAPI.getUsersbyID(id)
            Result.success(userDto.toDomainUser())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}