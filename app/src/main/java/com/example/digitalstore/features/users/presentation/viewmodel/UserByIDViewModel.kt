package com.example.digitalstore.features.users.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.users.domain.usecase.GetUsersByIDUseCase
import com.example.digitalstore.features.users.presentation.state.UserByIDUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserByIDViewModel @Inject constructor(
    private val getUsersByIDUseCase: GetUsersByIDUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserByIDUiState>(UserByIDUiState.Loading)
    val uiState: StateFlow<UserByIDUiState> = _uiState.asStateFlow()

    fun loadUserById(userID: Int) {
        viewModelScope.launch {
            _uiState.update { UserByIDUiState.Loading }

            getUsersByIDUseCase(userID).fold(
                onSuccess = { user ->
                    _uiState.update { UserByIDUiState.Success(user) }
                },
                onFailure = { error ->
                    val appError = (error as? AppError) ?: AppError.Unknown(error)
                    _uiState.update { UserByIDUiState.Error(appError) }
                }
            )
        }
    }
}