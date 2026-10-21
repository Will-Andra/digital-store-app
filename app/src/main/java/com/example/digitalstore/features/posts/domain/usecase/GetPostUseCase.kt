package com.example.digitalstore.features.posts.domain.usecase

import com.example.digitalstore.features.posts.domain.model.Posts
import com.example.digitalstore.features.posts.domain.repository.PostRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(): Result<List<Posts>>{
        return postRepository.getPosts()
    }

}