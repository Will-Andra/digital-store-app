package com.example.digitalstore.features.cart.domain.usecase

import com.example.digitalstore.features.cart.domain.model.Cart
import com.example.digitalstore.features.cart.domain.repository.CartRepository
import javax.inject.Inject

class GetCartsUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    suspend operator fun invoke(): Result<List<Cart>>{
        return cartRepository.getCarts()
    }

}