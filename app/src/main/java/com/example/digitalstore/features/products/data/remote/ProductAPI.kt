package com.example.digitalstore.features.products.data.remote

import com.example.digitalstore.features.products.data.dto.ProductDTO
import com.example.digitalstore.features.products.data.dto.ProductsResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductAPI {

    @GET("products?limit=0")
    suspend fun getProducts(): ProductsResponseDTO

    @GET("products/{id}")
    suspend fun getProductsByID(
        @Path("id") id: Int
    ): ProductDTO
}