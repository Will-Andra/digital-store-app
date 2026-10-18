package com.example.digitalstore.features.cart.presentation.state

import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.cart.domain.model.Cart

sealed interface CartByIDUiState {
    object Loading: CartByIDUiState
    data class Success (val carts: Cart): CartByIDUiState
    data class Error (val erro: AppError): CartByIDUiState
}