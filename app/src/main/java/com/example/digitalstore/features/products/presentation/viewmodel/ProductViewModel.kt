package com.example.digitalstore.features.products.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.products.domain.model.Products
import com.example.digitalstore.features.products.domain.usecase.GetProductsByIDUseCase
import com.example.digitalstore.features.products.domain.usecase.GetProductsUseCase
import com.example.digitalstore.features.products.presentation.state.ProductUiState
import com.example.digitalstore.features.users.domain.model.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._com_example_digitalstore_features_products_presentation_viewmodel_ProductByIDViewModel_HiltModules_BindsModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.temporal.TemporalQuery
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private var originalProduct: List<Products> = emptyList()
    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun loadProducts(){

        viewModelScope.launch {
            _uiState.update { ProductUiState.Loading }

            val result = getProductUseCase()

            result.fold(
                onSuccess = { products ->
                    //_uiState.update { ProductUiState.Success(products) }
                    originalProduct = products
                    filterProducts(_searchQuery.value)

                },
                onFailure = { error ->
                    val appError = (error as? AppError) ?: AppError.Unknown(error)
                    _uiState.update { ProductUiState.Error(appError) }
                }
            )
        }
    }

    private fun filterProducts(query: String) {
        if ( originalProduct.isEmpty() ){
            _uiState.update { ProductUiState.Success(emptyList()) }
            return
        }

        val filteredList = if ( query.isBlank() ){
            originalProduct
        }else {
            originalProduct.filter { product ->
                product.title.contains(query, ignoreCase = true) ||
                product.category.contains(query, ignoreCase = true)
            }
        }
        _uiState.update { ProductUiState.Success(filteredList) }
    }

    fun onSearchQueryChanged(query: String){
        _searchQuery.value = query
        filterProducts(query)
    }

}