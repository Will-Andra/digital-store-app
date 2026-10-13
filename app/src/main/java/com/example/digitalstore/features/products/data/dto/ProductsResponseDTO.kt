package com.example.digitalstore.features.products.data.dto

data class ProductsResponseDTO(
    val limit: Int,
    val products: List<ProductDTO>,
    val skip: Int,
    val total: Int
)