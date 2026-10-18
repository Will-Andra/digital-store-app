package com.example.digitalstore.features.products.presentation.state

import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.products.domain.model.Products

sealed interface ProductUiState {
    object Loading: ProductUiState
    data class Success (val products: List<Products>) : ProductUiState
    data class Error (val error: AppError): ProductUiState
}