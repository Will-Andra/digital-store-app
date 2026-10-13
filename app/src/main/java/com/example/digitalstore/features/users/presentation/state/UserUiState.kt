package com.example.digitalstore.features.users.presentation.state

import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.users.domain.model.Users

sealed interface UserUiState {
    object Loading : UserUiState
    data class Success(val users: List<Users>) : UserUiState
    data class Error(val error: AppError) : UserUiState
}