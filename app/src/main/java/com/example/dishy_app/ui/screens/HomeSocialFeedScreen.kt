package com.example.dishy_app.ui.screens


import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton


data class Place(
    val id: Int,
    val name: String,
    val description: String,
    val distance: String,
    val rating: Double,
    val reviews: Int
)


val samplePlaces = listOf(
    Place(1,
        "The Coffee Collective",
        "Cozy & Quiet",
        "0.5 mi",
        4.8,
        128),
    Place(2,
        "Nomad Workspace",
        "Productive",
        "1.2 mi",
        4.9,
        342),
    Place(3,
        "Bluebird Bistro",
        "Romantic",
        "2.8 mi",
        4.6,
        89),
    Place(2,
        "Nomad Workspace",
        "Productive",
        "1.2 mi",
        4.9,
        342),

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSocialFeedScreen() {

    var selectedFilter by remember {
        mutableStateOf("All")
    }

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
                            onClick = {  },
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
                            onClick = {  },
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
            // Barra de navegacion
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
            // Boton circular (dado)
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
            Column (
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    val filtros = listOf("All", "Cafes", "Restaurantes", "wesxrdcfgh", "waestzrxdyctfuvygibhuo", "qwertyuiokjnbv")

                    items(filtros) { filtro ->
                        FilterChip(
                            selected = selectedFilter == filtro,
                            onClick = { selectedFilter = filtro},
                            label = {
                                Text(filtro)
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color.Black,
                                selectedLabelColor = Color.White
                            )
                        )

                    }




                }




                LazyColumn (
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(samplePlaces) { place ->
                        PlaceCard(place = place)
                    }
                }
            }
        }
    }
}

// Barra de navegacion
@Composable
fun NavigationItem(icon: androidx.compose.ui.graphics.vector.ImageVector,
                   label: String, selected: Boolean) {
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

// Carta de los restaurantes
@Composable
fun PlaceCard(place: Place) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(
            horizontal = 16.dp,
            vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(
                text = place.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = place.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = place.distance,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⭐ ${place.rating}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = " (${place.reviews} reviews)",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeSocialFeedScreen()
    }
}
