package com.example.digitalstore.features.posts.data.remote

import com.example.digitalstore.features.posts.data.dto.PostDTO
import com.example.digitalstore.features.posts.data.dto.PostsResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface PostAPI {

    @GET("posts?limit=0")
    suspend fun getPosts(): PostsResponseDTO

    @GET ("posts/{id}")
    suspend fun getPostsByID(
        @Path("id") id: Int
    ): PostDTO
}