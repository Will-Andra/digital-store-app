package com.example.digitalstore.core.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.digitalstore.features.cart.presentation.screen.CartDetailsScreen
import com.example.digitalstore.features.cart.presentation.screen.CartScreen
import com.example.digitalstore.features.home.HomeScreen
import com.example.digitalstore.features.posts.presentation.ui.PostDetailScreen
import com.example.digitalstore.features.posts.presentation.ui.PostScreen
import com.example.digitalstore.features.products.presentation.screen.ProductDetailScreen
import com.example.digitalstore.features.products.presentation.screen.ProductScreen
import com.example.digitalstore.features.splash.SplashScreen // Certifica-te de importar o teu ecrã de splash
import com.example.digitalstore.features.users.presentation.screen.UserDetailScreen
import com.example.digitalstore.features.users.presentation.screen.UserScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "splash" // <-- 1. Começa agora pela Splash e não pela Home!

    ) {

        // 2. Adicionamos a rota da Splash Screen aqui em cima
        composable("splash") {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate("home") {
                        // 3. Limpa a splash da pilha para o botão voltar nunca mais voltar para trás
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onProductClick = {
                    navController.navigate("products")
                },
                onCartClick = {
                    navController.navigate("carts")
                },
                onUserClick = {
                    navController.navigate("users")
                },
                onPostClick = {
                    navController.navigate("posts")
                }
            )
        }

        composable("users") {
            UserScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onUserClick = { userId ->
                    navController.navigate("userDetail/$userId")
                }
            )
        }
        composable("products") {
            ProductScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onProductClick = { productId ->
                    navController.navigate("productDetail/$productId")
                }

            )
        }
        composable("carts"){
            CartScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onCartClick = { cartID ->
                    navController.navigate("cartDetail/$cartID")
                }
            )
        }

        composable("posts"){
            PostScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onPostClick = { postID ->
                    navController.navigate("postDetail/$postID")
                }
            )
        }

        ////////////////////////////////////////////////////////////

        composable(
            route = "userDetail/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            UserDetailScreen(
                userId = userId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(
                productId = productId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "cartDetail/{cartId}",
            arguments = listOf(navArgument("cartId") { type = NavType.IntType })
        ) { backStackEntry ->
            val cartId = backStackEntry.arguments?.getInt("cartId") ?: 0
            CartDetailsScreen(
                cartId = cartId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "postDetail/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId") ?: 0
            PostDetailScreen(
                postId = postId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}