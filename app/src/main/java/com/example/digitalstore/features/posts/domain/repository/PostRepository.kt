package com.example.digitalstore.features.posts.domain.repository
import com.example.digitalstore.features.posts.domain.model.Posts

interface PostRepository {

    suspend fun getPosts(): Result<List<Posts>>

    suspend fun getPostsByID(id: Int): Result<Posts>
}