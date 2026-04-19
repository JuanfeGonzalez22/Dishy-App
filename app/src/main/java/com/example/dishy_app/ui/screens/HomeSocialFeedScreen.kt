package com.example.dishy_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.dishy_app.ui.components.NavigationItem

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
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Search, "Search", tint = Color.Black)
                        }
                    },
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Notifications, "Notifications", tint = Color.Black)
                        }
                        // BOTÓN DE CERRAR SESIÓN
                        IconButton(
                            onClick = {
                                FirebaseAuthManager.signOut()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Logout, 
                                contentDescription = "Cerrar sesión",
                                tint = Color.Black
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NavigationItem(Icons.Default.Home, "Home", selected = true)
                        NavigationItem(Icons.Default.Public, "Map", selected = false, onClick = { navController.navigate("map") })
                        
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .offset(y = (-10).dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFF4A3D))
                                .clickable { navController.navigate("shake") },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Casino,
                                contentDescription = "Randomize",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        NavigationItem(Icons.Default.FavoriteBorder, "Saved", selected = false, onClick = { navController.navigate("saved_places") })
                        NavigationItem(Icons.Default.Person, "Profile", selected = false)
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filtros = listOf("All", "Cafes", "Restaurants", "Workspaces", "Bars")
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
            Icon(Icons.Default.ArrowForward, "Ver más", tint = Color.White, modifier = Modifier.size(18.dp))
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
