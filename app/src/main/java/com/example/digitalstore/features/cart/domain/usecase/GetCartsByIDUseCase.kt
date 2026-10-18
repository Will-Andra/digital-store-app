package com.example.digitalstore.features.cart.domain.usecase

import com.example.digitalstore.features.cart.domain.model.Cart
import com.example.digitalstore.features.cart.domain.repository.CartRepository
import javax.inject.Inject

class GetCartsByIDUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    suspend operator fun invoke(cartId: Int): Result<Cart>{
        return cartRepository.getCartsByID(cartId)
    }

}