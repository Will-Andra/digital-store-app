package com.example.digitalstore.features.posts.data.mapper

import com.example.digitalstore.features.posts.data.dto.PostDTO
import com.example.digitalstore.features.posts.data.dto.ReactionsDTO
import com.example.digitalstore.features.posts.domain.model.Posts

fun PostDTO.toDomainPosts(): Posts{
    return Posts(
        body = body,
        id = id,
        reactions = reactions,
        title = title,
        userId = userId,
        views = views
    )
}