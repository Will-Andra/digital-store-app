package com.example.digitalstore.features.users.presentation.screen

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.digitalstore.R
import com.example.digitalstore.core.util.Constantes
import com.example.digitalstore.features.users.domain.model.Users
import com.example.digitalstore.features.users.presentation.state.UserByIDUiState
import com.example.digitalstore.features.users.presentation.viewmodel.UserByIDViewModel
import com.example.digitalstore.ui.theme.DigitalStoreTheme

@Composable
fun UserDetailScreen(
    userId: Int,
    viewModel: UserByIDViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadUserById(userId)
    }

    UserDetailScreenContent(
        uiState = uiState,
        onBackClick = onBackClick
    )
}

@Composable
fun UserDetailScreenContent(
    uiState: UserByIDUiState,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            UserDetailTopBar(
                titleText = "User Details",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(15.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            when (val state = uiState) {
                is UserByIDUiState.Loading -> {
                    CircularProgressIndicator(
                        color = Color(Constantes.BLUE_MAINCOLOR)
                    )
                }
                is UserByIDUiState.Success -> {
                    UserDetailContent(user = state.user)
                }
                is UserByIDUiState.Error -> {
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
fun UserDetailTopBar(
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

@Composable
fun UserDetailContent(user: Users) {
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = user.image,
                contentDescription = "User picture of ${user.firstName}",
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
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(Constantes.BLUE_MAINCOLOR)
                    )
                    Text(
                        text = "ID: ${user.id}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(Constantes.BLUE_MAINCOLOR)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = user.fullName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Age: ${user.age}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = null,
                    tint = Color(Constantes.BLUE_MAINCOLOR)
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailScreenPreview() {
    DigitalStoreTheme {
        UserDetailScreenContent(
            uiState = UserByIDUiState.Success(
                user = Users(
                    id = 1,
                    age = 30,
                    email = "willian.silva@email.com",
                    firstName = "Willian",
                    lastName = "Silva",
                    image = R.drawable.ic_perfil
                )
            ),
            onBackClick = {}
        )
    }
}