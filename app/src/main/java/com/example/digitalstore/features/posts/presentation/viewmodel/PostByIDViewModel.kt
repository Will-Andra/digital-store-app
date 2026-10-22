package com.example.digitalstore.features.posts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.posts.domain.usecase.GetPostByIDUseCase
import com.example.digitalstore.features.posts.domain.usecase.GetPostUseCase
import com.example.digitalstore.features.posts.presentation.state.PostByIDUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostByIDViewModel @Inject constructor(
    private val useCase: GetPostByIDUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<PostByIDUiState>(PostByIDUiState.Loading)
    val uiState: StateFlow<PostByIDUiState> = _uiState.asStateFlow()

    fun loadPostByID(postId: Int){

        viewModelScope.launch {
            _uiState.update { PostByIDUiState.Loading }

            useCase(postId).fold(
                onSuccess = {post->
                    _uiState.update { PostByIDUiState.Success(post) }
                },
                onFailure = {erro->
                    val appError = (erro as? AppError)?: AppError.Unknown(erro)
                    _uiState.update { PostByIDUiState.Error(appError) }
                }
            )


        }

    }

}