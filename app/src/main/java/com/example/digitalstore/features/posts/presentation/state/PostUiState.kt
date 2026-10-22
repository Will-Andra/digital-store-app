package com.example.digitalstore.features.posts.presentation.state

import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.posts.domain.model.Posts

sealed interface PostUiState {
    object Loading: PostUiState
    data class Success(val post: List<Posts>): PostUiState
    data class Error(val appError: AppError): PostUiState
}