package com.example.digitalstore.features.users.domain.usecase

import com.example.digitalstore.features.users.data.repository.UserRepositoryImpl
import com.example.digitalstore.features.users.domain.model.Users
import com.example.digitalstore.features.users.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersByIDUseCase @Inject constructor (
    private val userRepository: UserRepository
){

    suspend operator fun invoke(userID: Int): Result<Users>{
        return userRepository.getUsersByID(userID)
    }

}