package com.example.digitalstore.core.di

import androidx.compose.runtime.retain.retainManagedRetainedValuesStore
import com.example.digitalstore.features.users.data.remote.UserAPI
import com.example.digitalstore.core.util.Constantes
import com.example.digitalstore.features.cart.data.remote.CartAPI
import com.example.digitalstore.features.cart.data.repository.CartRepositoryImpl
import com.example.digitalstore.features.cart.domain.repository.CartRepository
import com.example.digitalstore.features.posts.data.remote.PostAPI
import com.example.digitalstore.features.posts.data.repository.PostRepositoryImpl
import com.example.digitalstore.features.posts.domain.repository.PostRepository
import com.example.digitalstore.features.products.data.remote.ProductAPI
import com.example.digitalstore.features.products.data.repository.ProductRepositoryImpl
import com.example.digitalstore.features.products.domain.repository.ProductRepository
import com.example.digitalstore.features.users.data.repository.UserRepositoryImpl
import com.example.digitalstore.features.users.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .client(okHttpClient)
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

    @Provides
    fun providesPostAPI(retrofit: Retrofit): PostAPI{
        return retrofit.create(PostAPI::class.java)
    }

    @Provides
    fun providesPostRepository(postAPI: PostAPI, userRepository: UserRepository): PostRepository{
        return PostRepositoryImpl(postAPI, userRepository)
    }


}