package com.example.digitalstore.features.posts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstore.core.domain.AppError
import com.example.digitalstore.features.posts.domain.model.Posts
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
) : ViewModel() {

    private val _uiState = MutableStateFlow<PostUiState>(PostUiState.Loading)
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    //
    private var originalPosts: List<Posts> = emptyList()
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun loadPost() {
        viewModelScope.launch {
            _uiState.update {
                PostUiState.Loading
            }

            useCase().fold(
                onSuccess = { posts ->
                    //_uiState.update { PostUiState.Success(posts)}
                    originalPosts = posts
                    filterPosts(_searchQuery.value)
                },
                onFailure = { error ->
                    val appErro = (error as? AppError) ?: AppError.Unknown(error)
                    _uiState.update { PostUiState.Error(appErro) }
                }
            )
        }
    }

    private fun filterPosts(query: String) {
        if (originalPosts.isEmpty()) {
            _uiState.update { PostUiState.Success(emptyList()) }
            return
        }

        val filteredList = if (query.isBlank()) {
            originalPosts
        } else {
            originalPosts.filter { post ->
                post.title.contains(query, ignoreCase = true) ||
                post.body.contains(query, ignoreCase = true)
            }
        }
        _uiState.update { PostUiState.Success(filteredList) }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        filterPosts(query)
    }
}
