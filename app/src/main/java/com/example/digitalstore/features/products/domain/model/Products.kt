package com.example.digitalstore.features.products.domain.model

import com.example.digitalstore.features.products.data.dto.Dimensions

data class Products(
    val id: Int,
    val brand: String,
    val category: String,
    val description: String,
    val dimensions: Dimensions,
    val price: Double,
    val shippingInformation: String,
    val thumbnail: String,
    val title: String,
    val weight: Int
)
