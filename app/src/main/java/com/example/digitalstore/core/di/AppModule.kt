package com.example.digitalstore.core.di

import androidx.compose.runtime.retain.retainManagedRetainedValuesStore
import com.example.digitalstore.features.users.data.remote.UserAPI
import com.example.digitalstore.core.util.Constantes
import com.example.digitalstore.features.cart.data.remote.CartAPI
import com.example.digitalstore.features.cart.data.repository.CartRepositoryImpl
import com.example.digitalstore.features.cart.domain.repository.CartRepository
import com.example.digitalstore.features.products.data.remote.ProductAPI
import com.example.digitalstore.features.products.data.repository.ProductRepositoryImpl
import com.example.digitalstore.features.products.domain.repository.ProductRepository
import com.example.digitalstore.features.users.data.repository.UserRepositoryImpl
import com.example.digitalstore.features.users.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesUserAPI(retrofit: Retrofit): UserAPI{
        return retrofit.create(UserAPI::class.java)
    }

    @Provides
    fun providesUserRepository(userAPI: UserAPI): UserRepository{
        return UserRepositoryImpl(userAPI)
    }

    @Provides
    fun providesProductAPI(retrofit: Retrofit): ProductAPI{
        return retrofit.create(ProductAPI::class.java)
    }

    @Provides
    fun providesProductRepository(productAPI: ProductAPI): ProductRepository{
        return ProductRepositoryImpl(productAPI)
    }

    @Provides
    fun providesCartAPI(retrofit: Retrofit): CartAPI{
        return retrofit.create(CartAPI::class.java)
    }

    @Provides
    fun providesCartRepository(cartAPI: CartAPI): CartRepository {
        return CartRepositoryImpl(cartAPI)
    }


}