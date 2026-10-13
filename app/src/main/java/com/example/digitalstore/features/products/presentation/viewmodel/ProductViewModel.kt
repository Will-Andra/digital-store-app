package com.example.digitalstore.features.products.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.products.domain.model.Products
import com.example.digitalstore.features.products.domain.usecase.GetProductsByIDUseCase
import com.example.digitalstore.features.products.domain.usecase.GetProductsUseCase
import com.example.digitalstore.features.products.presentation.state.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    fun loadProducts(){
        viewModelScope.launch {
            _uiState.update { ProductUiState.Loading }

            getProductUseCase().fold(
                onSuccess = {products ->
                    _uiState.update { ProductUiState.Success(products) }
                },
                onFailure = {error ->
                    val appError = (error as? AppError)?: AppError.Unknown(error)
                    _uiState.update { ProductUiState.Error(appError) }
                }
            )
        }
    }
}