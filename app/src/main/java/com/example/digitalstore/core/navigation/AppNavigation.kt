package com.example.digitalstore.core.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.digitalstore.features.home.HomeScreen
import com.example.digitalstore.features.users.presentation.component.UserDetailScreen
import com.example.digitalstore.features.users.presentation.component.UserScreen
import com.example.digitalstore.features.users.presentation.viewmodel.UserViewModel

@Composable
fun AppNavigation(){

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable("home"){
            HomeScreen(
                onProductClick = {
                    Toast.makeText(context, "Products coming soon", Toast.LENGTH_SHORT).show()
                },
                onCartClick = {
                    Toast.makeText(context, "Cart coming soon", Toast.LENGTH_SHORT).show()
                },
                onUserClick = {
                    navController.navigate("users")
                },
                onPostClick = {
                    Toast.makeText(context, "Posts coming soon", Toast.LENGTH_SHORT).show()
                }
            )
        }

        composable("users"){

            UserScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onUserClick = { userId ->
                    navController.navigate("userDetail/$userId")
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
    }
}