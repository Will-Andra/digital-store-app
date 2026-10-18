package com.example.digitalstore.core.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.digitalstore.features.home.HomeScreen
import com.example.digitalstore.features.products.presentation.screen.ProductDetailScreen
import com.example.digitalstore.features.products.presentation.screen.ProductScreen
import com.example.digitalstore.features.users.presentation.screen.UserDetailScreen
import com.example.digitalstore.features.users.presentation.screen.UserScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onProductClick = {
                    navController.navigate("products")
                },
                onCartClick = {
                    Toast.makeText(context, "CartDTO coming soon", Toast.LENGTH_SHORT).show()
                },
                onUserClick = {
                    navController.navigate("users")
                },
                onPostClick = {
                    Toast.makeText(context, "Posts coming soon", Toast.LENGTH_SHORT).show()
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
    }
}