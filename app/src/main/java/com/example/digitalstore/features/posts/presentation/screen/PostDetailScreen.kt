package com.example.digitalstore.features.posts.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
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
import com.example.digitalstore.core.util.Constantes
import com.example.digitalstore.features.posts.domain.model.Posts
import com.example.digitalstore.features.posts.presentation.state.PostByIDUiState
import com.example.digitalstore.features.posts.presentation.viewmodel.PostByIDViewModel


@Composable
fun PostDetailScreen(
    viewModel: PostByIDViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    postId: Int
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(postId) {
        viewModel.loadPostByID(postId)
    }

    PostDetailContent(
        uiState = uiState,
        onBackClick = onBackClick
    )
}

@Composable
fun PostDetailContent(
    uiState: PostByIDUiState,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            PostDetailTopBar(
                titleText = "Post Details",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is PostByIDUiState.Loading -> {
                    CircularProgressIndicator(
                        color = Color(Constantes.BLUE_MAINCOLOR)
                    )
                }
                is PostByIDUiState.Success -> {

                    PostDetailBody(post = uiState.post)
                }
                is PostByIDUiState.Error -> {
                    Text(
                        text = uiState.erro.toMessageError(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailTopBar(
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
fun PostDetailBody(post: Posts) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                //
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AsyncImage(
                            model = post.userImage,
                            contentDescription = "Avatar of ${post.userName}",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Column {
                            Text(
                                text = post.userName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "User ID: #${post.userId}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(Constantes.BLUE_MAINCOLOR).copy(alpha = 0.12f),
                        tonalElevation = 2.dp
                    ) {
                        Text(
                            text = "ID: #${post.id}",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(Constantes.BLUE_MAINCOLOR)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                //
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                //
                Text(
                    text = post.body,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(24.dp))

                //
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Views",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = "${post.views} views",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            //imageVector = Icons.Default.Favorite,
                            contentDescription = "Views",
                            modifier = Modifier.size(18.dp),
                            tint = Color(color = Constantes.BLUE_MAINCOLOR)
                        )

                        Text(
                            text = "${post.reactions.likes}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(Constantes.BLUE_MAINCOLOR)
                        )
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailContentPreview() {
    val samplePost = Posts(
        id = 1,
        title = "Mastering Jetpack Compose with Clean Architecture and Hilt",
        body = "In this detailed article, we explore how to build modern Android applications robustly. We cover everything from dependency injection with Hilt to reactive state management with StateFlow, ensuring a smooth user experience and highly testable code.",
        userId = 101,
        userName = "Willian Developer",
        userImage = "https://via.placeholder.com/150",
        views = 1540,
        reactions = com.example.digitalstore.features.posts.data.dto.ReactionsDTO(likes = 230, dislikes = 4)
    )

    MaterialTheme {
        Surface {
            PostDetailContent(
                uiState = PostByIDUiState.Success(samplePost),
                onBackClick = {}
            )
        }
    }
}

