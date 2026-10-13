package com.example.digitalstore.features.products.domain.usecase

import com.example.digitalstore.features.products.domain.model.Products
import com.example.digitalstore.features.products.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(): Result<List<Products>>{
        return repository.getProducts()
    }

}