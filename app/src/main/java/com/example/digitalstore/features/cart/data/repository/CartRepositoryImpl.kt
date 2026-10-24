package com.example.digitalstore.features.cart.data.repository

import com.example.digitalstore.core.util.safeCall
import com.example.digitalstore.features.cart.data.mapper.toDomainCart
import com.example.digitalstore.features.cart.data.remote.CartAPI
import com.example.digitalstore.features.cart.domain.model.Cart
import com.example.digitalstore.features.cart.domain.repository.CartRepository
import com.example.digitalstore.features.users.domain.repository.UserRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartAPI: CartAPI,
    private val userRepository: UserRepository
): CartRepository {

    override suspend fun getCarts(): Result<List<Cart>> {
        return safeCall {
            val response = cartAPI.getCarts()
            val usersResult = userRepository.getUsers()

            val usersMap = usersResult.getOrNull()?.associateBy { it.id } ?: emptyMap()

            response.carts.map { cart ->
                val user = usersMap[cart.userId]
                val userName = user.let { "${it?.firstName} ${it?.lastName}" } ?: "Unknown User"
                cart.toDomainCart(userName = userName)
            }
        }
    }

    override suspend fun getCartsByID(id: Int): Result<Cart> {
        return safeCall {
            val cartDTO = cartAPI.getCartsByID(id)
            val userResult = userRepository.getUsersByID(cartDTO.userId)

            val user = userResult.getOrNull()
            val userName = user?.let { "${it.firstName} ${it.lastName}" } ?: "Unknown User"

            cartDTO.toDomainCart(userName = userName)
        }
    }

}