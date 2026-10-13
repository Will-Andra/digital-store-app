package com.example.digitalstore.features.products.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.products.domain.usecase.GetProductsByIDUseCase
import com.example.digitalstore.features.products.domain.usecase.GetProductsUseCase
import com.example.digitalstore.features.products.presentation.state.ProductByIDUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductByIDViewModel @Inject constructor(
    private val getProductByIDUseCase: GetProductsByIDUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<ProductByIDUiState>(ProductByIDUiState.Loading)
    val uiState: StateFlow<ProductByIDUiState> = _uiState.asStateFlow()

    fun loadProductByID(productID: Int){
        viewModelScope.launch {
            _uiState.update { ProductByIDUiState.Loading }

            getProductByIDUseCase(productID).fold(
                onSuccess = {product ->
                    _uiState.update { ProductByIDUiState.Success(product) }
                },
                onFailure = {error ->
                    val appError = (error as? AppError)?: AppError.Unknown(error)
                    _uiState.update { ProductByIDUiState.Error(appError) }
                }
            )
        }
    }
}