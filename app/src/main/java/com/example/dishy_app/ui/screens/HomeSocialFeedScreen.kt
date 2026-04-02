package com.example.dishy_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dishy_app.R
import androidx.navigation.compose.rememberNavController


data class Place(
    val id: Int,
    val name: String,
    val description: String,
    val distance: String,
    val rating: Double,
    val reviews: Int,
    val imageRes: Int,       // <-- nuevo campo para la imagen
    val communityPhotos: List<Int>,
    val lat: Double,
    val lng: Double// <-- nuevo campo para las fotos de la comunidad
)


val samplePlaces = listOf(
    Place(
        id = 1,
        name = "The Coffee Collective",
        description = "Cozy & Quiet",
        distance = "0.5 mi",
        rating = 4.8,
        reviews = 128,
        imageRes = R.drawable.coffee,       // imagen: res/drawable/coffee.jpg
        communityPhotos = listOf(
            R.drawable.photo1_coffee,
            R.drawable.photo2_coffee,
            R.drawable.photo3_coffee
        ),
        lat = 4.5339,
        lng = -75.6811
    ),
    Place(
        id = 2,
        name = "Nomad Workspace",
        description = "Productive",
        distance = "1.2 mi",
        rating = 4.9,
        reviews = 342,
        imageRes = R.drawable.workspace,     // imagen: res/drawable/workspace.jpg
        communityPhotos = listOf(
            R.drawable.photo1_coffee,
            R.drawable.photo2_coffee,
            R.drawable.photo3_coffee
        ),
        lat = 4.5350,
        lng = -75.6820
    ),
    Place(
        id = 3,
        name = "Bluebird Bistro",
        description = "Romantic",
        distance = "2.8 mi",
        rating = 4.6,
        reviews = 89,
        imageRes = R.drawable.bistro,       // imagen: res/drawable/bistro.jpg
        communityPhotos = listOf(
            R.drawable.photo1_coffee,
            R.drawable.photo2_coffee,
            R.drawable.photo3_coffee
        ),
        lat = 4.5360,
        lng = -75.6800
    )
)


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
                        IconButton(
                            onClick = { },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Black
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
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
                        NavigationItem(Icons.Default.Public, "Map", selected = false)
                        Spacer(modifier = Modifier.width(48.dp))
                        NavigationItem(Icons.Default.FavoriteBorder, "Saved", selected = false)
                        NavigationItem(Icons.Default.Person, "Profile", selected = false)
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { },
                    containerColor = Color(0xFFFF4A3D),
                    contentColor = Color.White,
                    shape = CircleShape,
                ) {
                    Icon(
                        imageVector = Icons.Default.Casino,
                        contentDescription = "Randomize",
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Fila de filtros
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
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

                // Lista de cards
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
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
        // Imagen de fondo
        Image(
            painter = painterResource(id = place.imageRes),
            contentDescription = place.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Degradado oscuro en la parte inferior
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

        // Texto sobre la imagen
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = place.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = place.description,
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFFFF4A3D),
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = place.distance,
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
            }
            Text(
                text = "⭐ ${place.rating} (${place.reviews} reviews)",
                fontSize = 12.sp,
                color = Color.LightGray
            )
        }

        // Botón flecha rojo abajo a la derecha
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFFFF4A3D)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Ver más",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}


@Composable
fun NavigationItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean
) {
    val color = if (selected) Color(0xFFFF4A3D) else Color.Gray
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = color,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeSocialFeedScreen(navController = rememberNavController())
    }
}