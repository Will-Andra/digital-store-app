package com.example.digitalstore.features.cart.domain.model

import com.example.digitalstore.features.cart.data.dto.ProductCartDTO

data class Cart(
    val discountedTotal: Double,
    val id: Int,
    val products: List<ProductCart>,
    val total: Double,
    val totalProducts: Int,
    val totalQuantity: Int,
    val userId: Int

)
