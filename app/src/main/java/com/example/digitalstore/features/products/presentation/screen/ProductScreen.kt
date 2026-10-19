package com.example.digitalstore.features.products.presentation.screen

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.digitalstore.R
import com.example.digitalstore.core.util.Constantes
import com.example.digitalstore.features.products.data.dto.Dimensions
import com.example.digitalstore.features.products.domain.model.Products
import com.example.digitalstore.features.products.presentation.state.ProductUiState
import com.example.digitalstore.features.products.presentation.viewmodel.ProductViewModel
import com.example.digitalstore.ui.theme.DigitalStoreTheme

@Composable
fun ProductScreen(
    viewmodel: ProductViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    val uiState by viewmodel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewmodel.loadProducts()
    }

    ProductScreenContent(
        uiState = uiState,
        onProductClick = onProductClick,
        onBackClick = onBackClick

    )
}

@Composable
fun ProductScreenContent(
    uiState: ProductUiState,
    onProductClick: (Int) -> Unit,
    onBackClick: () -> Unit

) {
    Scaffold(
        topBar = {
            ProductTopBar(
                titletext = "Products",
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
            when( val state = uiState){
                is ProductUiState.Loading -> {
                    CircularProgressIndicator(
                        color = Color(Constantes.BLUE_MAINCOLOR)
                    )
                }
                is ProductUiState.Success ->{
                    ProductList(
                        products = state.products,
                        onProductClick = onProductClick
                    )
                }
                is ProductUiState.Error -> {
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
fun ProductList(
    products: List<Products>,
    onProductClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items  = products,
            key = {product-> product.id}
        )
        { product ->
            ProductItem(
                product = product,
                onClick = { onProductClick(product.id) }
            )
        }
    }
}

@Composable
fun ProductItem(
    product: Products,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = "ProductCart picture ${product.title}",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),  //Tirar
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = Color(Constantes.BLUE_MAINCOLOR).copy(alpha = 0.12f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ShoppingBag,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = Color(Constantes.BLUE_MAINCOLOR)
                            )
                            Text(
                                text = "ID: ${product.id}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(Constantes.BLUE_MAINCOLOR)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    //horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Price: $ ",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = product.price.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(Constantes.BLUE_MAINCOLOR),
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }


} //End Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductTopBar(
    titletext: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = titletext,
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
fun ProductScreenPreview() {
    DigitalStoreTheme {
        ProductScreenContent(
            uiState = ProductUiState.Success(
                products = listOf(
                    Products(
                        id = 1,
                        brand = "No Brand available",
                        category = "No Category available",
                        description = "No description",
                        dimensions = Dimensions(0.0,0.0,0.0),
                        price = 0.00,
                        shippingInformation = "No shippingInformation",
                        thumbnail = R.drawable.ic_perfil,
                        title = "No title available",
                        weight = 1
                    )
                )
            ),
            onBackClick = {},
            onProductClick = {}
        )
    }

}