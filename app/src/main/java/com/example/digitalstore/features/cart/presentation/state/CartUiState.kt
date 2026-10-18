package com.example.digitalstore.features.cart.presentation.state

import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.cart.domain.model.Cart

sealed interface CartUiState {
    object Loading: CartUiState
    data class Success(val cart:List<Cart>): CartUiState
    data class Error(val error: AppError ): CartUiState
}