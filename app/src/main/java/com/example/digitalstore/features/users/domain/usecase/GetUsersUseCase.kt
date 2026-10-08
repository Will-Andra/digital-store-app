package com.example.digitalstore.features.users.domain.usecase

import com.example.digitalstore.features.users.domain.model.Users
import com.example.digitalstore.features.users.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(): Result<List<Users>>{
        return repository.getUsers()
    }

}