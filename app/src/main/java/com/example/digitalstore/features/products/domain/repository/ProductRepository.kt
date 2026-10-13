package com.example.digitalstore.features.products.domain.repository

import com.example.digitalstore.features.products.domain.model.Products

interface ProductRepository {

    suspend fun getProducts(): Result<List<Products>>

    suspend fun getProductsByID(id: Int): Result<Products>

}