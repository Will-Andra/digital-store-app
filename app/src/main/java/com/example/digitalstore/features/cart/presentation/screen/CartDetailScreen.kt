package com.example.digitalstore.features.cart.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.digitalstore.core.util.Constantes
import com.example.digitalstore.features.cart.domain.model.Cart
import com.example.digitalstore.features.cart.domain.model.ProductCart
import com.example.digitalstore.features.cart.presentation.state.CartByIDUiState
import com.example.digitalstore.features.cart.presentation.viewmodel.CartByIDViewModel
import com.example.digitalstore.ui.theme.DigitalStoreTheme
import java.util.Locale

@Composable
fun CartDetailsScreen(
    cartId: Int,
    viewModel: CartByIDViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(cartId) {
        viewModel.loadCartByID(cartId)
    }

    CartDetailsScreenContent(
        uiState = uiState,
        onBackClick = onBackClick
    )
}

@Composable
fun CartDetailsScreenContent(
    uiState: CartByIDUiState,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CartDetailsTopBar(
                titleText = "Cart Details",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is CartByIDUiState.Loading -> {
                    CircularProgressIndicator(
                        color = Color(Constantes.BLUE_MAINCOLOR)
                    )
                }

                is CartByIDUiState.Success -> {
                    CartDetailsContent(cart = state.carts)
                }

                is CartByIDUiState.Error -> {
                    Text(
                        text = state.erro.toMessageError(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun CartDetailsContent(cart: Cart) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        //
        CartSummaryHeaderCard(cart = cart)

        Spacer(modifier = Modifier.height(16.dp))

        //
        Text(
            text = "Products: ${cart.products.size} items / ${cart.totalQuantity} units",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        //
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = cart.products,
                key = { product -> product.id }
            ) { product ->
                ProductCartItemCard(product = product)
            }
        }
    }
}

@Composable
fun CartSummaryHeaderCard(cart: Cart) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(Constantes.BLUE_MAINCOLOR).copy(alpha = 0.08f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Cart #${cart.id}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Units: ${cart.totalQuantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$ ${String.format(Locale.US, "%,.2f", cart.total)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(Constantes.BLUE_MAINCOLOR)
                )
            }
        }
    }
}

@Composable
fun ProductCartItemCard(
    product: ProductCart
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "ID: ${product.id} | Qty: ${product.quantity}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Unit Price: $ ${String.format(Locale.US, "%,.2f", product.price)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Subtotal: $ ${String.format(Locale.US, "%,.2f", product.total)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(Constantes.BLUE_MAINCOLOR)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDetailsTopBar(
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
                    contentDescription = "Back",
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
fun CartScreenDetailsPreview() {
    DigitalStoreTheme {
        CartDetailsScreenContent(
            uiState = CartByIDUiState.Success(
                carts = Cart(
                    discountedTotal = 13037.88,
                    id = 1,
                    products = listOf(
                        ProductCart(
                            discountPercentage = 10.0,
                            discountedTotal = 10547.97,
                            id = 162,
                            price = 3999.99,
                            quantity = 3,
                            thumbnail = "",
                            title = "Generic Motorcycle",
                            total = 11999.97
                        )
                    ),
                    total = 13037.88,
                    totalProducts = 4,
                    totalQuantity = 12,
                    userId = 1
                )
            ),
            onBackClick = {}
        )
    }
}