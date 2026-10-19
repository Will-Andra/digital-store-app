package com.example.digitalstore.features.products.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.digitalstore.core.util.Constantes
import com.example.digitalstore.features.products.data.dto.Dimensions
import com.example.digitalstore.features.products.domain.model.Products
import com.example.digitalstore.features.products.presentation.state.ProductByIDUiState
import com.example.digitalstore.features.products.presentation.viewmodel.ProductByIDViewModel
import com.example.digitalstore.ui.theme.DigitalStoreTheme
import kotlin.Int
import kotlin.String
import com.example.digitalstore.R

@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductByIDViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProductByID(productId)
    }

    ProductDetailScreenContent(
        uiState = uiState,
        onBackClick = onBackClick
    )
}

@Composable
fun ProductDetailScreenContent(
    uiState: ProductByIDUiState,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            ProductDetailTopBar(
                titletext = "ProductCart Details",
                onBackClick = onBackClick
            )
        }
    ) { innerpadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerpadding)
                .padding(15.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            when (val state = uiState) {
                is ProductByIDUiState.Loading -> {
                    CircularProgressIndicator(
                        color = Color(Constantes.BLUE_MAINCOLOR)
                    )
                }

                is ProductByIDUiState.Success -> {
                    ProductDetailContent(product = uiState.product)
                }

                is ProductByIDUiState.Error -> {
                    Text(
                        text = state.error.toMessageError(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailTopBar(
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
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Product Screen",
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

@Composable
fun ProductDetailContent(product: Products) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = product.thumbnail,
                contentDescription = "ProductCart picture${product.thumbnail}",
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = MaterialTheme.shapes.small,
                color = Color(Constantes.BLUE_MAINCOLOR).copy(alpha = 0.12f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingBag,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(Constantes.BLUE_MAINCOLOR)
                    )
                    Text(
                        text = "ID: ${product.id}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(Constantes.BLUE_MAINCOLOR)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${product.title} ",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(Constantes.BLUE_MAINCOLOR)
                )
                Text(
                    text = "Price: $",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.price.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color(Constantes.BLUE_MAINCOLOR)
                )

            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(Constantes.BLUE_MAINCOLOR)
                )
                Text(
                    text = "Brand:",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.brand?: "No brand"
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(Constantes.BLUE_MAINCOLOR)
                )
                Text(
                    text = "Category:",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.category
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(Constantes.BLUE_MAINCOLOR)
                )
                Text(
                    text = "Dimension:",
                    fontWeight = FontWeight.Bold
                )

                val depth = product.dimensions.depth
                val height = product.dimensions.height
                val width = product.dimensions.width

                Text(
                    text = "Depth: ${depth.toString()} \n" +
                           "Height: ${height.toString()} \n" +
                           "Width: ${width.toString()}"
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(Constantes.BLUE_MAINCOLOR)
                )
                Text(
                    text = "Shipping Info:",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.shippingInformation
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(Constantes.BLUE_MAINCOLOR)
                )
                Text(
                    text = "Weight:",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.weight.toString()
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(Constantes.BLUE_MAINCOLOR)
                )
                Text(
                    text = "Description:",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.description
                )
            }

        }//end Collumn
    }
}


@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    DigitalStoreTheme {
        ProductDetailScreenContent(
            uiState = ProductByIDUiState.Success(
                product = Products(
                    id = 1,
                    brand = "Brand New",
                    category = "Category New",
                    description = "Description",
                    dimensions = Dimensions(0.0, 0.0, 0.0),
                    price = 0.00,
                    shippingInformation = "Information",
                    thumbnail = R.drawable.ic_perfil,
                    title = "ProductCart Title",
                    weight = 1
                )
            ),
            onBackClick = {}
        )
    }
}