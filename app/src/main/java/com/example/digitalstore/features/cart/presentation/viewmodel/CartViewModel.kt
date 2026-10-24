package com.example.digitalstore.features.cart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.cart.domain.model.Cart
import com.example.digitalstore.features.cart.domain.usecase.GetCartsUseCase
import com.example.digitalstore.features.cart.presentation.state.CartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCase: GetCartsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private var originalList: List<Cart> = emptyList()

    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun loadCarts(){
        viewModelScope.launch {
            _uiState.update { CartUiState.Loading }

            val result = cartUseCase()
            result.fold(
                onSuccess = { cart ->
                    //_uiState.update { CartUiState.Success(cart) }
                    originalList = cart
                    filterCart(_searchQuery.value)
                },
                onFailure = { error ->
                    val appError = (error as? AppError)?: AppError.Unknown(error)
                    _uiState.update { CartUiState.Error(appError) }
                }
            )
        }
    }

    private fun filterCart(query: String) {
        if ( originalList.isEmpty() ){
            _uiState.update { CartUiState.Success(emptyList()) }
            return
        }

        val filterList = if ( query.isBlank() ){
            originalList
        }else{
            originalList.filter { cart ->
                cart.userName.contains(query, ignoreCase = true)||
                cart.userId.toString().contains(query)
            }
        }
        _uiState.update { CartUiState.Success(filterList) }
    }

    fun onSearchQueryChanged(query: String){
        _searchQuery.value = query
        filterCart(query)
    }


}