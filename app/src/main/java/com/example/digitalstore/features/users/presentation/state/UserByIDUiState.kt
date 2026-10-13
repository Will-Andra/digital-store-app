package com.example.digitalstore.features.users.presentation.state

import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.users.domain.model.Users

sealed interface UserByIDUiState {
    object Loading : UserByIDUiState
    data class Success(val user: Users) : UserByIDUiState
    data class Error(val error: AppError) : UserByIDUiState
}