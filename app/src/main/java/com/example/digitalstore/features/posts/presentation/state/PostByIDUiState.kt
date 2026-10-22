package com.example.digitalstore.features.posts.presentation.state

import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.posts.domain.model.Posts

sealed interface PostByIDUiState {
    object Loading: PostByIDUiState
    data class Success(val post: Posts): PostByIDUiState
    data class Error(val erro: AppError): PostByIDUiState
}