package com.example.digitalstore.core.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.digitalstore.features.home.HomeScreen

@Composable
fun AppNavigation(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable ("home"){
            HomeScreen(
                onUserClick = {
                    navController.navigate("users")
                },
                onProductClick = {
                    navController.navigate("products")
                },
                onCartClick = {
                    navController.navigate("carts")
                },
                onPostClick = {
                    navController.navigate("posts")
                }
            )
        }

        composable("users"){
            //UserScreen()
        }
        composable ("products"){
            //ProductScreen()
        }
        composable ("carts"){
            //CartScreen()
        }
        composable ("posts"){
            //PostScreen()
        }

        composable(
            route = "user_detail/userId"
        ){

        }
        composable(
            route = "product_detail/productId"
        ){

        }
        composable (
            route = "cart_detail/cartID"
        ){

        }
        composable (
            route = "post_detail/postID"
        ){

        }
    }
}