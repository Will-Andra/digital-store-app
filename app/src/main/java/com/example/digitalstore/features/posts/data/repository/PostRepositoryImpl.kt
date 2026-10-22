package com.example.digitalstore.features.posts.data.repository

import com.example.digitalstore.core.util.safeCall
import com.example.digitalstore.features.posts.data.mapper.toDomainPosts
import com.example.digitalstore.features.posts.data.remote.PostAPI
import com.example.digitalstore.features.posts.domain.model.Posts
import com.example.digitalstore.features.posts.domain.repository.PostRepository
import com.example.digitalstore.features.users.domain.repository.UserRepository
import javax.inject.Inject


class PostRepositoryImpl @Inject constructor(
    private val postAPI: PostAPI,
    private val userRepository: UserRepository //
): PostRepository {

    override suspend fun getPosts(): Result<List<Posts>> {
       return safeCall {
           val response = postAPI.getPosts()
           val usersResult = userRepository.getUsers()

           val usersMap = usersResult.getOrNull()?.associateBy { it.id } ?: emptyMap()

           response.posts.map { post ->

               val user = usersMap[post.userId]
               val userName = user.let { "${it?.firstName} ${it?.lastName}" } ?: "Unknown User"
               val userImage = user?.image ?: ""
               post.toDomainPosts(userName = userName, userImage = userImage)
           }
       }
    }

    override suspend fun getPostsByID(id: Int): Result<Posts> {
        return safeCall {
            val postDto = postAPI.getPostsByID(id)
            val userResult = userRepository.getUsersByID(postDto.userId)

            val user = userResult.getOrNull()

            val userName = user?.let { "${it.firstName} ${it.lastName}" } ?: "Unknown User"
            val userImage = user?.image ?: ""

            postDto.toDomainPosts(userName = userName, userImage = userImage)
        }
    }
}