package com.example.digitalstore.features.posts.data.repository

import com.example.digitalstore.core.util.safeCall
import com.example.digitalstore.features.posts.data.mapper.toDomainPosts
import com.example.digitalstore.features.posts.data.remote.PostAPI
import com.example.digitalstore.features.posts.domain.model.Posts
import com.example.digitalstore.features.posts.domain.repository.PostRepository
import javax.inject.Inject


class PostRepositoryImpl @Inject constructor(
    private val postAPI: PostAPI
): PostRepository {
    override suspend fun getPosts(): Result<List<Posts>> {
       return safeCall {
           val response = postAPI.getPosts()
           response.posts.map { post ->
               post.toDomainPosts()
           }
       }
    }

    override suspend fun getPostsByID(id: Int): Result<Posts> {
        return safeCall {
            val response = postAPI.getPostsByID(id)
            response.toDomainPosts()
        }
    }
}