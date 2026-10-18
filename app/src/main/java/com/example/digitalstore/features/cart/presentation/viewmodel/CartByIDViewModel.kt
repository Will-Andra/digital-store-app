package com.example.digitalstore.features.cart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.cart.domain.usecase.GetCartsByIDUseCase
import com.example.digitalstore.features.cart.presentation.state.CartByIDUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartByIDViewModel @Inject constructor(
    private val cartByIDUseCase: GetCartsByIDUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<CartByIDUiState>(CartByIDUiState.Loading)
    val uiState: StateFlow<CartByIDUiState> = _uiState.asStateFlow()

    fun getCartByID(cartId: Int){

        viewModelScope.launch {

            _uiState.update { CartByIDUiState.Loading }

            val result = cartByIDUseCase(cartId)
            result.fold(
                onSuccess = { cart ->
                    _uiState.update { CartByIDUiState.Success(cart) }
                },
                onFailure = { error ->
                    val appError = (error as? AppError)?: AppError.Unknown(error)
                    _uiState.update { CartByIDUiState.Error(appError) }
                }
            )
        }
    }
}