package com.example.digitalstore.features.cart.data.dto

data class CartsResponseDTO(
    val carts: List<CartDTO>,
    val limit: Int,
    val skip: Int,
    val total: Int
)