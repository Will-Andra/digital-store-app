package com.example.digitalstore.features.posts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.posts.domain.usecase.GetPostUseCase
import com.example.digitalstore.features.posts.presentation.state.PostUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val useCase: GetPostUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<PostUiState>(PostUiState.Loading)
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    fun loadPost(){
        viewModelScope.launch {
            _uiState.update {
                PostUiState.Loading
            }

            useCase().fold(
                onSuccess = { posts ->
                    _uiState.update { PostUiState.Success(posts)}
                },
                onFailure = {error->
                    val appErro = (error as? AppError)?: AppError.Unknown(error)
                    _uiState.update { PostUiState.Error(appErro) }
                }
            )
        }
    }
}