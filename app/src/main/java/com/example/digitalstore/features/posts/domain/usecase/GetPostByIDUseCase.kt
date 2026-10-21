package com.example.digitalstore.features.posts.domain.usecase

import com.example.digitalstore.features.posts.domain.model.Posts
import com.example.digitalstore.features.posts.domain.repository.PostRepository
import javax.inject.Inject

class GetPostByIDUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(postId: Int): Result<Posts>{
        return postRepository.getPostsByID(postId)
    }

}