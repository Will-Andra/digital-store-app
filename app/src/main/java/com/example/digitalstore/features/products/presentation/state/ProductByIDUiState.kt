package com.example.digitalstore.features.products.presentation.state

import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.products.domain.model.Products

sealed interface ProductByIDUiState{
    object Loading: ProductByIDUiState
    data class Success(val product: Products) : ProductByIDUiState
    data class Error (val error: AppError): ProductByIDUiState
}