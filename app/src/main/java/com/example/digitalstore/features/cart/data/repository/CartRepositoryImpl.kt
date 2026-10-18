package com.example.digitalstore.features.cart.data.repository

import com.example.digitalstore.core.util.safeCall
import com.example.digitalstore.features.cart.data.mapper.toDomainCart
import com.example.digitalstore.features.cart.data.remote.CartAPI
import com.example.digitalstore.features.cart.domain.model.Cart
import com.example.digitalstore.features.cart.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartAPI: CartAPI
): CartRepository {
    override suspend fun getCarts(): Result<List<Cart>> {
        return safeCall {
            val response = cartAPI.getCarts()
            response.carts.map { cart ->
                cart.toDomainCart()
            }
        }
    }

    override suspend fun getCartsByID(id: Int): Result<Cart> {
        return safeCall {
            val response = cartAPI.getCartsByID(id)
            response.toDomainCart()
        }
    }

}