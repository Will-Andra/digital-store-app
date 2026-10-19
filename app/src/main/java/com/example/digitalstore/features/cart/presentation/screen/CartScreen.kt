package com.example.digitalstore.features.cart.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.digitalstore.core.util.Constantes
import com.example.digitalstore.features.cart.domain.model.Cart
import com.example.digitalstore.features.cart.domain.model.ProductCart
import com.example.digitalstore.features.cart.presentation.state.CartUiState
import com.example.digitalstore.features.cart.presentation.viewmodel.CartViewModel
import com.example.digitalstore.ui.theme.DigitalStoreTheme
import kotlin.Int
import kotlin.collections.List

@Composable
fun CartScreen(
    viewmodel: CartViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onCartClick: (Int) -> Unit,
) {
    val uiState by viewmodel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewmodel.loadCarts()
    }

    CartScreenContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onCartClick = onCartClick
    )
}

@Composable
fun CartScreenContent(
    uiState: CartUiState,
    onBackClick: () -> Unit,
    onCartClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CartTopBar(
                titleText = "Carts",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 15.dp),
            contentAlignment = Alignment.Center,
        ) {
            when (val state = uiState) {
                is CartUiState.Loading -> {
                    CircularProgressIndicator(
                        color = Color(Constantes.BLUE_MAINCOLOR)
                    )
                }

                is CartUiState.Success -> {
                    CartList(
                        carts = state.cart,
                        onCartClick = onCartClick
                    )
                }

                is CartUiState.Error -> {
                    Text(
                        text = state.error.toMessageError(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun CartList(
    carts: List<Cart>,
    onCartClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = carts,
            key =  {cart-> cart.id})
        { cart ->
            CartItem(
                cart = cart,
                onClick = { onCartClick(cart.id) }
            )
        }
    }
}

@Composable
fun CartItem(
    cart: Cart,
    onClick: (Int) -> Unit
) {
    Card(
        onClick = { onClick(cart.id) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Box(
                    modifier = Modifier
                        .size(58.dp)
                        .background(
                            color = Color(Constantes.BLUE_MAINCOLOR).copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = Color(Constantes.BLUE_MAINCOLOR)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Cart ID: ${cart.id}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Items: ${cart.totalProducts} | Units: ${cart.totalQuantity}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$ ${String.format("%, .2f", cart.total)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(Constantes.BLUE_MAINCOLOR)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopBar(
    titleText: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = titleText,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Homescreen",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(Constantes.BLUE_MAINCOLOR),
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    DigitalStoreTheme {
        CartScreenContent(
            uiState = CartUiState.Success(
                cart = listOf(
                    Cart(
                        discountedTotal = 0.00,
                        id = 0,
                        products = listOf(
                            ProductCart(
                                0.0,
                                0.0,
                                0,
                                0.0,
                                0,
                                "",
                                "",
                                0.0
                            )
                        ),
                        total = 0.0,
                        totalProducts = 0,
                        totalQuantity = 0,
                        userId = 0
                    )
                )
            ),
            onBackClick = {},
            onCartClick = {}
        )
    }
}