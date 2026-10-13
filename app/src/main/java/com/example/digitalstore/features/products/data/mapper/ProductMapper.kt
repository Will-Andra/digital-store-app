package com.example.digitalstore.features.products.data.mapper

import com.example.digitalstore.features.products.data.dto.ProductDTO
import com.example.digitalstore.features.products.domain.model.Products

fun ProductDTO.toDomainProduct(): Products {
    return Products(
        id = id,
        brand = brand,
        category = category,
        description = description,
        dimensions = dimensions,
        price = price,
        shippingInformation = shippingInformation,
        thumbnail = thumbnail,
        title = title,
        weight = weight
    )
}