package com.example.digitalstore.features.products.data.dto

data class ProductDTO(
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