package com.example.digitalstore.features.cart.data.mapper

import com.example.digitalstore.features.cart.data.dto.CartDTO
import com.example.digitalstore.features.cart.domain.model.Cart

fun CartDTO.toDomainCart(userName: String): Cart{
    return Cart(
        discountedTotal = discountedTotal,
        id = id,
        products = products.map { it.toDomainProductCart() },
        total = total,
        totalProducts = totalProducts,
        totalQuantity = totalQuantity,
        userId = userId,
        userName = userName  //
    )
}