package com.example.digitalstore.features.posts.data.mapper

import com.example.digitalstore.features.posts.data.dto.ReactionsDTO
import com.example.digitalstore.features.posts.domain.model.Reactions
import kotlin.Int

fun ReactionsDTO.toMapperReactions(): Reactions {
    return Reactions(
        dislikes = dislikes,
        likes = likes
    )
}