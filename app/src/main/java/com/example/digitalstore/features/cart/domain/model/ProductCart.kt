package com.example.digitalstore.features.cart.domain.model

data class ProductCart(
    val discountPercentage: Double,
    val discountedTotal: Double,
    val id: Int,
    val price: Double,
    val quantity: Int,
    val thumbnail: Any,  //String
    val title: String,
    val total: Double
)
