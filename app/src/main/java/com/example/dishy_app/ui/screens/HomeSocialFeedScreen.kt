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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.dishy_app.FirebaseAuthManager
import com.example.dishy_app.ui.components.BottomBarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSocialFeedScreen(navController: androidx.navigation.NavController) {
    var selectedFilter by remember { mutableStateOf("All") }

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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { }) {
                                Icon(Icons.Default.Search, "Search", tint = Color.Black)
                            }
                            IconButton(onClick = { navController.navigate("camera") }) {
                                Icon(Icons.Default.Add, "New Post", tint = Color.Black)
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Notifications, "Notifications", tint = Color.Black)
                        }
                        IconButton(onClick = { FirebaseAuthManager.signOut() }) {
                            Icon(Icons.AutoMirrored.Filled.Logout, "Cerrar sesión", tint = Color.Black)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("camera") },
                    containerColor = Color(0xFFFF4A3D),
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.AddAPhoto, "New Post")
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
                    val filtros = listOf("All", "For you", "Cafes", "Restaurants", "Workspaces", "Bars")
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

                // --- CAMBIO DE LAYOUT ---
                if (selectedFilter == "For you") {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp), // Padding menor para look de IG
                        contentPadding = PaddingValues(bottom = 16.dp),
                        verticalItemSpacing = 4.dp, // Espacio mínimo entre fotos
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(samplePosts) { post ->
                            StaggeredVibeCard(
                                post = post,
                                onClick = { navController.navigate("post_detail/${post.id}") }
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(samplePlaces) { place ->
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

// --- COMPONENTE ESTILO INSTAGRAM (SOLO FOTO) ---
@Composable
fun StaggeredVibeCard(post: DishyPost, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) // Bordes un poco más cerrados para look profesional
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = post.imageUrl,
            contentDescription = post.placeName,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )

        // PEQUEÑO INDICADOR DE VIBRA (Sutil, arriba a la derecha)
        Icon(
            imageVector = if (post.vibeSpecs.wifiSpeed == "High Speed") Icons.Default.Wifi else Icons.Default.Bolt,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.8f),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(16.dp)
        )
    }
}

@Composable
fun PlaceCard(place: Place, onClick: () -> Unit = {}) {
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
            contentDescription = place.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xCC000000)),
                        startY = 100f
                    )
                )
        )
        Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
        ) {
            Text(place.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(place.description, fontSize = 13.sp, color = Color.LightGray)
                Spacer(Modifier.width(12.dp))
                Icon(Icons.Default.LocationOn, null, tint = Color(0xFFFF4A3D), modifier = Modifier.size(14.dp))
                Text(place.distance, fontSize = 13.sp, color = Color.LightGray)
            }
            Text("⭐ ${place.rating} (${place.reviews} reviews)", fontSize = 12.sp, color = Color.LightGray)
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFFFF4A3D)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, "Ver más", tint = Color.White, modifier = Modifier.size(18.dp))
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeSocialFeedScreen(navController = rememberNavController())
    }
}