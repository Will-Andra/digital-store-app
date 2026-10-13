package com.example.digitalstore.features.products.data.repository

import com.example.digitalstore.core.util.safeCall
import com.example.digitalstore.features.products.data.mapper.toDomainProduct
import com.example.digitalstore.features.products.data.remote.ProductAPI
import com.example.digitalstore.features.products.domain.model.Products
import com.example.digitalstore.features.products.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productAPI: ProductAPI
): ProductRepository {

    override suspend fun getProducts(): Result<List<Products>> {
        return safeCall {
            val response = productAPI.getProducts()
            response.products.map { product ->
                product.toDomainProduct()
            }
        }
    }

    override suspend fun getProductsByID(id: Int): Result<Products> {
        return safeCall {
            val response = productAPI.getProductsByID(id)
            response.toDomainProduct()
        }
    }
}