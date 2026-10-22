package com.example.digitalstore.features.posts.domain.model

import com.example.digitalstore.features.posts.data.dto.ReactionsDTO

data class Posts(
    val body: String,
    val id: Int,
    val reactions: ReactionsDTO,
    val title: String,
    val userId: Int,
    val userName: String, //
    val userImage: Any, //
    val views: Int
)