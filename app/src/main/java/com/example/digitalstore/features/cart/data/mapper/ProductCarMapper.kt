package com.example.digitalstore.features.cart.data.mapper

import com.example.digitalstore.features.cart.data.dto.ProductCartDTO
import com.example.digitalstore.features.cart.domain.model.ProductCart

fun ProductCartDTO.toDomainProductCart(): ProductCart{
    return ProductCart(
        discountPercentage = discountPercentage,
        discountedTotal = discountedTotal,
        id = id,
        price = price,
        quantity = quantity,
        thumbnail = thumbnail,
        title = title,
        total = total
    )
}