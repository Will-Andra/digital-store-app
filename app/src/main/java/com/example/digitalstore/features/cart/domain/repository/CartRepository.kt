package com.example.digitalstore.features.cart.domain.repository

import com.example.digitalstore.features.cart.data.dto.CartDTO
import com.example.digitalstore.features.cart.domain.model.Cart

interface CartRepository {

    suspend fun getCarts(): Result<List<Cart>>

    suspend fun getCartsByID(id: Int): Result<Cart>

}