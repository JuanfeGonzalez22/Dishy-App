package com.example.dishy_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.dishy_app.ui.components.BottomBarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedPlacesScreen(navController: NavController,
                      ) {

    var selectedTab by remember { mutableStateOf("Want to Go") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Saved Places",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search")
                        }
                    }
                )
            },
            bottomBar = {
                BottomBarComponent(
                    currentRoute = "saved_places",
                    onNavigate = { route -> navController.navigate(route) }
                )

            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                TabSelector(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text("Your Collection",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Text("2 places",
                        color = Color.Gray,
                        fontSize = 12.sp)
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(samplePlaces) { place ->
                        SavedPlaceCard(place = place)
                    }
                }
            }
        }
    }
}

@Composable
fun TabSelector(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFF1F3F4))
            .padding(4.dp)
    ) {
        val tabs = listOf("Want to Go", "Visited")
        tabs.forEach { tab ->
            val isSelected = selectedTab == tab
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTabSelected(tab) },
                shape = RoundedCornerShape(20.dp),
                color = if (isSelected) Color.White else Color.Transparent,
                shadowElevation = if (isSelected) 2.dp else 0.dp
            ) {
                Text(
                    text = tab,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    color = if (isSelected) Color(0xFFFF4A3D) else Color.Gray,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun SavedPlaceCard(place: Place) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)) {
                AsyncImage(
                    model = place.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Botón Corazón Rojo
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(32.dp),
                    shape = CircleShape,
                    color = Color.White
                ) {
                    Icon(Icons.Default.Favorite, null, tint = Color.Red, modifier = Modifier.padding(6.dp))
                }
                // Rating flotante
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black.copy(alpha = 0.6f)
                ) {
                    Text("⭐ ${place.rating}", color = Color.White, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontSize = 12.sp)
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(place.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Surface(color = Color(0xFFFFEBEE), shape = RoundedCornerShape(8.dp)) {
                        Text("Cafe", color = Color(0xFFFF4A3D), fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                }
                Text(place.description, color = Color.Gray, fontSize = 13.sp)

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    VibeTag("Laptop Friendly")
                    VibeTag("Quiet")
                    VibeTag("$$")
                }
            }
        }
    }
}

@Composable
fun VibeTag(text: String) {
    Surface(color = Color(0xFFF1F3F4), shape = RoundedCornerShape(8.dp)) {
        Text(text, color = Color.Gray, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SavedPlacesPreview() {
    MaterialTheme {
        SavedPlacesScreen(navController = rememberNavController())
    }
}
