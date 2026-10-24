package com.example.digitalstore.features.users.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.users.domain.model.Users
import com.example.digitalstore.features.users.domain.usecase.GetUsersUseCase
import com.example.digitalstore.features.users.presentation.state.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._com_example_digitalstore_features_posts_presentation_viewmodel_PostByIDViewModel_HiltModules_BindsModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    private var originalUsers: List<Users> = emptyList()
    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.update { UserUiState.Loading }

            getUsersUseCase().fold(
                onSuccess = { users ->
                    //_uiState.update { UserUiState.Success(users) }
                    originalUsers = users
                    filterUsers(_searchQuery.value)
                },
                onFailure = { error ->
                    val appError = (error as? AppError) ?: AppError.Unknown(error)
                    _uiState.update { UserUiState.Error(appError) }
                }
            )
        }
    }

    private fun filterUsers(query: String) {
        if ( originalUsers.isEmpty() ) {
            _uiState.update { UserUiState.Success(emptyList()) }
            return
        }

        val filteredList = if ( query.isBlank()) {
            originalUsers
        }else{
            originalUsers.filter { user ->
                user.fullName.contains(query,ignoreCase = true) ||
                user.email.contains(query,ignoreCase = true)
            }
        }
        _uiState.update { UserUiState.Success(filteredList) }
    }

    fun onSearchQueryChanged(query: String){
        _searchQuery.value =  query
        filterUsers(query)
    }
}
