package com.example.digitalstore.features.posts.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.digitalstore.core.util.Constantes
import com.example.digitalstore.features.posts.domain.model.Posts
import com.example.digitalstore.features.posts.presentation.components.PostEmptyState
import com.example.digitalstore.features.posts.presentation.state.PostUiState
import com.example.digitalstore.features.posts.presentation.viewmodel.PostViewModel
import com.example.digitalstore.features.users.presentation.screen.UserTopBar

@Composable
fun PostScreen(
    viewModel: PostViewModel = hiltViewModel(),
    onPostClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadPost()
    }

    PostScreenContent(
        uiState = uiState,
        onPostClick = onPostClick,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreenContent(
    modifier: Modifier = Modifier,
    uiState: PostUiState,
    onPostClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            PostTopBar(
                titleText = "Posts",
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
                is PostUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is PostUiState.Success -> {
                   if (uiState.post.isEmpty()) {
                    // if (true) { //Use to test Empty post
                        PostEmptyState()
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(uiState.post) { post ->
                                PostItem(
                                    post = post,
                                    onClick = { onPostClick(post.id) }
                                )
                            }
                        }//

                    }
                }

                is PostUiState.Error -> {
                    Text(
                        text = "Erro ao carregar posts: ${uiState.appError}",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostTopBar(
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
                    contentDescription = "Back to  Homescreen",
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
fun PostItem(
    post: Posts,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            //
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AsyncImage(
                        model = post.userImage,
                        contentDescription = "Avatar de ${post.userName}",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = post.userName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }


                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(color = Constantes.BLUE_MAINCOLOR).copy(alpha = 0.12f),
                    tonalElevation = 2.dp
                ) {
                    Text(
                        text = "ID: #${post.id}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(Constantes.BLUE_MAINCOLOR)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = "Views",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Text(
                    text = "${post.views} visualizações",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostScreenContentPreview() {
    val samplePosts = listOf(
        Posts(
            id = 1,
            title = "Learning Jetpack Compose in Practice with Clean Architecture",
            body = "In this article, we explore how to build modern Android applications using reactive state, Hilt, and clean architecture.",
            userId = 101,
            userName = "Willian Developer",
            userImage = "https://via.placeholder.com/150",
            views = 342,
            reactions = com.example.digitalstore.features.posts.data.dto.ReactionsDTO(
                likes = 45,
                dislikes = 2
            )
        )
    )

    MaterialTheme {
        Surface {
            PostScreenContent(
                uiState = PostUiState.Success(samplePosts),
                onPostClick = {},
                onBackClick = {}
            )
        }
    }
}