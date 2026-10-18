package com.example.digitalstore.features.cart.data.dto

data class CartDTO(
    val discountedTotal: Double,
    val id: Int,
    val products: List<ProductCartDTO>,
    val total: Double,
    val totalProducts: Int,
    val totalQuantity: Int,
    val userId: Int
)