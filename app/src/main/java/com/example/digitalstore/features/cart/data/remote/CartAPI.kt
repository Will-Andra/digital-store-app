package com.example.digitalstore.features.cart.data.remote

import com.example.digitalstore.features.cart.data.dto.CartDTO
import com.example.digitalstore.features.cart.data.dto.CartsResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface CartAPI {

    @GET("carts?limit=0")
    suspend fun getCarts(): CartsResponseDTO

    @GET("carts/{id}")
    suspend fun getCartsByID(
        @Path("id") id: Int
    ): CartDTO

}