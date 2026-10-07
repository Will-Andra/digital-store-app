package com.example.digitalstore.features.home

import android.R
import android.app.Activity
import android.view.RoundedCorner
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.digitalstore.ui.theme.Blue80
import com.example.digitalstore.ui.theme.DigitalStoreTheme
import com.example.digitalstore.ui.theme.Purple40
import com.example.digitalstore.ui.theme.Purple80
import com.example.digitalstore.util.Constantes
import java.util.function.IntConsumer

@Composable
fun HomeScreen(
    onUserClick: () -> Unit,
    onProductClick: () -> Unit,
    onCartClick: () -> Unit,
    onPostClick: () -> Unit
) {

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Blue80.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    Scaffold(
        topBar = {
            TopBarContainer(
                titleText = "Digital Store"
            )
        }
    ) { paddingValues ->
        ScreenArea(
            paddingValues = paddingValues,
            modifier = Modifier.fillMaxSize(),
            onUserClick = onUserClick,
            onProductClick = onProductClick,
            onCartClick = onCartClick,
            onPostClick = onPostClick
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarContainer(
    titleText: String
) {
    TopAppBar(
        title = {
            Text(
                text = titleText,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(Constantes.BLUE_MAINCOLOR),
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}


@Composable
fun ScreenArea(
    paddingValues: PaddingValues,
    modifier: Modifier,
    onUserClick: () -> Unit,
    onProductClick: () -> Unit,
    onCartClick: () -> Unit,
    onPostClick: () -> Unit,
) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding()
            )
            .padding(
                all = 15.dp
            )
    ) {
        Spacer(
            modifier = Modifier.height(24.dp)
        )
        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.headlineLarge,
            color = Color(Constantes.BLUE_MAINCOLOR),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Choose an option to continue"
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            HomeCardMenu(
                title = "Users",
                description = "Consult the app's users.",
                icon = Icons.Outlined.People,
                modifier = Modifier.weight(1f),
                onClick = {
                    Toast.makeText(context, "Navigating to Users...", Toast.LENGTH_SHORT).show()
                    //onUserClick()
                }
            )
            HomeCardMenu(
                title = "Products",
                description = "Explore our wide range of products.",
                icon = Icons.Outlined.ShoppingBag,
                modifier = Modifier.weight(1f),
                onClick = {
                    Toast.makeText(context, "Navigating to Products...", Toast.LENGTH_SHORT).show()
                    //onProductClick()
                }
            )

        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            HomeCardMenu(
                title = "Cart",
                description = "Check your shopping cart.",
                icon = Icons.Outlined.ShoppingCart,
                modifier = Modifier.weight(1f),
                onClick = {
                    Toast.makeText(context, "Navigating to Cart...", Toast.LENGTH_SHORT).show()
                    //onCartClick()
                }
            )
            HomeCardMenu(
                title = "Posts",
                description = "Check out our latest news and updates.",
                icon = Icons.Outlined.CameraAlt,
                modifier = Modifier.weight(1f),
                onClick = {
                    Toast.makeText(context, "Navigating to Post...", Toast.LENGTH_SHORT).show()
                    //onPostClick()
                }
            )
        }

    }

}

@Composable
fun HomeCardMenu(
    title: String,
    description: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(215.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = description,
                    modifier = modifier.size(45.dp),
                    tint = Color(Constantes.BLUE_MAINCOLOR)
                )
            }//End Box

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(
                    modifier = Modifier.height(6.dp)
                )
                Text(
                    text = description
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    DigitalStoreTheme {
        HomeScreen(
            onUserClick = {},
            onProductClick = {},
            onCartClick = {},
            onPostClick = {},
        )
    }
}
