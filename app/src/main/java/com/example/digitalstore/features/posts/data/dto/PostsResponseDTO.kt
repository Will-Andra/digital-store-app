package com.example.digitalstore.features.posts.data.dto

data class PostsResponseDTO(
    val limit: Int,
    val posts: List<PostDTO>,
    val skip: Int,
    val total: Int
)