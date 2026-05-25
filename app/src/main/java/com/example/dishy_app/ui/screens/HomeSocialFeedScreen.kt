package com.example.dishy_app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dishy_app.FirebaseAuthManager
import com.example.dishy_app.data.model.DishyPost
import com.example.dishy_app.data.model.Place
import com.example.dishy_app.ui.components.BottomBarComponent
import com.example.dishy_app.ui.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSocialFeedScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    var selectedFilter by remember { mutableStateOf("For you") }
    var isSearchExpanded by remember { mutableStateOf(false) }
    
    val places = viewModel.places
    val posts = viewModel.posts
    val favoritePostIds = viewModel.favoritePostIds
    val favoritePlaceIds = viewModel.favoritePlaceIds
    val isLoading = viewModel.isLoading
    val searchQuery = viewModel.searchQuery

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        if (!isSearchExpanded) {
                            Text(
                                text = "Dishy",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF4A3D),
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { isSearchExpanded = !isSearchExpanded }) {
                            Icon(
                                imageVector = if (isSearchExpanded) Icons.Default.Close else Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Black
                            )
                        }
                    },
                    actions = {
                        if (!isSearchExpanded) {
                            IconButton(onClick = { navController.navigate("camera") }) {
                                Icon(Icons.Default.AddAPhoto, "New Post", tint = Color.Black)
                            }
                            IconButton(onClick = { FirebaseAuthManager.signOut() }) {
                                Icon(Icons.AutoMirrored.Filled.Logout, "Logout", tint = Color.Black)
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
                )
                
                AnimatedVisibility(
                    visible = isSearchExpanded,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.onSearchQueryChange(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        placeholder = { Text("Search places, vibes, or categories...") },
                        leadingIcon = { Icon(Icons.Default.Search, null) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                                    Icon(Icons.Default.Clear, null)
                                }
                            }
                        },
                        shape = RoundedCornerShape(24.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFFF4A3D),
                            unfocusedBorderColor = Color.LightGray
                        )
                    )
                }
            }
        },
        bottomBar = {
            BottomBarComponent(
                currentRoute = "home",
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isLoading && posts.isEmpty() && places.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFFF4A3D))
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                // Filtros
                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filtros = listOf("For you", "All", "Cafes", "Restaurants", "Workspaces", "Bars")
                    items(filtros) { filtro ->
                        FilterChip(
                            selected = selectedFilter == filtro,
                            onClick = { selectedFilter = filtro },
                            label = { Text(filtro) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color.Black,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                // Feed
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    if (selectedFilter == "For you") {
                        if (posts.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier.fillParentMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        if (searchQuery.isEmpty()) "No posts yet. Be the first!" 
                                        else "No results found for \"$searchQuery\"", 
                                        color = Color.Gray
                                    )
                                }
                            }
                        } else {
                            items(posts) { post ->
                                val isFavorite = favoritePostIds.contains(post.id)
                                PostCard(
                                    post = post,
                                    isFavorite = isFavorite,
                                    onClick = { navController.navigate("post_detail/${post.id}") },
                                    onFavoriteClick = { viewModel.toggleFavoritePost(post.id) },
                                    onArrowClick = {
                                        if (post.authorRole == "BUSINESS" || post.authorRole == "RESTAURANT") {
                                            navController.navigate("post_detail/${post.id}")
                                        } else {
                                            navController.navigate("profile?userId=${post.userId}")
                                        }
                                    }
                                )
                            }
                        }
                    } else {
                        val filteredPlaces = if (selectedFilter == "All") places else places.filter { it.category == selectedFilter }
                        
                        if (filteredPlaces.isEmpty()) {
                            item {
                                Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                                    Text("No $selectedFilter found", color = Color.Gray)
                                }
                            }
                        } else {
                            items(filteredPlaces) { place ->
                                val isFavorite = favoritePlaceIds.contains(place.id)
                                PlaceCard(
                                    place = place,
                                    isFavorite = isFavorite,
                                    onClick = { navController.navigate("detail/${place.id}") },
                                    onFavoriteClick = { viewModel.toggleFavoritePlace(place.id) }
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
fun PostCard(
    post: DishyPost, 
    isFavorite: Boolean,
    onClick: () -> Unit, 
    onFavoriteClick: () -> Unit,
    onArrowClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(320.dp)
            .clip(RoundedCornerShape(28.dp))
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = post.imageUrl,
            contentDescription = post.placeName,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradiente inferior para legibilidad
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = 300f
                    )
                )
        )

        // Botón Favorito arriba a la izquierda
        Surface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(40.dp)
                .clickable { onFavoriteClick() },
            color = Color.White.copy(alpha = 0.8f),
            shape = CircleShape
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFavorite) Color.Red else Color.Black,
                modifier = Modifier.padding(8.dp).size(24.dp)
            )
        }

        // Badge de Vibe arriba a la derecha
        Surface(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            color = Color.Black.copy(alpha = 0.3f),
            shape = CircleShape
        ) {
            Icon(
                imageVector = if (post.vibeSpecs.wifiSpeed == "High Speed") Icons.Default.Wifi else Icons.Default.Bolt,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(8.dp).size(20.dp)
            )
        }

        // Información en la parte inferior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = post.placeName.ifBlank { "New Discovery" },
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        null,
                        tint = Color(0xFFFF4A3D),
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = post.location.ifBlank { "Armenia, Quindío" },
                        fontSize = 12.sp,
                        color = Color.LightGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            // Botón de flecha
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF4A3D))
                    .clickable { onArrowClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun PlaceCard(
    place: Place, 
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(320.dp)
            .clip(RoundedCornerShape(28.dp))
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
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = 300f
                    )
                )
        )

        // Botón Favorito arriba a la izquierda
        Surface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(40.dp)
                .clickable { onFavoriteClick() },
            color = Color.White.copy(alpha = 0.8f),
            shape = CircleShape
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFavorite) Color.Red else Color.Black,
                modifier = Modifier.padding(8.dp).size(24.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(place.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.LocationOn, null, tint = Color(0xFFFF4A3D), modifier = Modifier.size(14.dp))
                    Text(place.category, fontSize = 12.sp, color = Color.LightGray)
                }
            }
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF4A3D)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
        }
    }
}
