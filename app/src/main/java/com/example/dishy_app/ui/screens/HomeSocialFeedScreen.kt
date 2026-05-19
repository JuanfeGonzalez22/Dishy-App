package com.example.dishy_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.dishy_app.FirebaseAuthManager
import com.example.dishy_app.data.model.Place
import com.example.dishy_app.data.model.DishyPost
import com.example.dishy_app.ui.components.BottomBarComponent
import com.example.dishy_app.ui.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSocialFeedScreen(
    navController: androidx.navigation.NavController,
    viewModel: HomeViewModel = viewModel()
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val places = viewModel.places
    val posts = viewModel.posts
    val isLoading = viewModel.isLoading

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Dishy",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF4A3D),
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Search, "Search", tint = Color.Black)
                        }
                    },
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Notifications, "Notifications", tint = Color.Black)
                        }
                        IconButton(onClick = { FirebaseAuthManager.signOut() }) {
                            Icon(Icons.AutoMirrored.Filled.Logout, "Logout", tint = Color.Black)
                        }
                    }
                )
            },
            bottomBar = {
                BottomBarComponent(
                    currentRoute = "home",
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
        ) { paddingValues ->
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFFF4A3D))
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // SECCIÓN DE FILTROS
                    LazyRow(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val filtros = listOf("All", "For you", "Cafes", "Restaurants", "Workspaces")
                        items(filtros) { filtro ->
                            FilterChip(
                                selected = selectedFilter == filtro,
                                onClick = { selectedFilter = filtro },
                                label = { Text(filtro) }
                            )
                        }
                    }

                    if (selectedFilter == "For you") {
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
                            contentPadding = PaddingValues(bottom = 16.dp),
                            verticalItemSpacing = 4.dp,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(posts) { post ->
                                StaggeredVibeCard(
                                    post = post,
                                    onClick = { /* Navegar a detalle de post si existe */ }
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(places) { place ->
                                PlaceCard(
                                    place = place,
                                    onClick = { navController.navigate("detail/${place.id}") }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StaggeredVibeCard(post: DishyPost, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = post.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PlaceCard(place: Place, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = place.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(0.7f))))
        )
        Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
        ) {
            Text(place.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(place.category, fontSize = 14.sp, color = Color.LightGray)
        }
    }
}
