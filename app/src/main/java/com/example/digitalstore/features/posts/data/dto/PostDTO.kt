package com.example.digitalstore.features.posts.data.dto

data class PostDTO(
    val body: String,
    val id: Int,
    val reactions: ReactionsDTO,
    val tags: List<String>,
    val title: String,
    val userId: Int,
    val views: Int
)