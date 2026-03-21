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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class Restaurant(
    val name: String,
    val rating: Double,
    val cuisine: String,
    val description: String,
    val followers: Int
)

val sampleRestaurant = Restaurant(
    name = "La Terraza Gourmet",
    rating = 4.8,
    cuisine = "Fine Dining · Mediterranean",
    description = "Experience the finest Mediterranean flavors in an upscale, vibrant atmosphere. Perfect for romantic dinners and special celebrations with a stunning terrace view.",
    followers = 1240
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantProfileScreen() {
    Scaffold(
        topBar = { TopBar() },
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
                    NavigationItem(Icons.Default.Home, "Home", selected = false)
                    NavigationItem(Icons.Default.Public, "Map", selected = false)
                    
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .offset(y = (-12).dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFF4A3D))
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Casino,
                            contentDescription = "Randomize",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    NavigationItem(Icons.Default.FavoriteBorder, "Saved", selected = false)
                    NavigationItem(Icons.Default.Person, "Profile", selected = false)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                BannerSection()
            }

            item {
                RestaurantInfoSection(sampleRestaurant)
            }

            item {
                ActionTabsBar()
            }

            // 3. Cuadrícula de fotos del restaurante (Grid de 3 columnas)
            val photos = List(9) { "https://picsum.photos/400/400?random=$it" }
            items(photos.chunked(3)) { rowPhotos ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    rowPhotos.forEach { photoUrl ->
                        AsyncImage(
                            model = photoUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}

@Composable
fun BannerSection() {
    Box(modifier = Modifier.fillMaxWidth().height(260.dp)) {
        // Imagen de fondo principal
        AsyncImage(
            model = "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4",
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(200.dp),
            contentScale = ContentScale.Crop
        )

        // Logo y Botones superpuestos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            // El cuadro del Logo
            Surface(
                modifier = Modifier.size(100.dp),
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 8.dp,
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(2.dp, Color.White)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Restaurant,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = Color.LightGray
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))

            // Botones Follow y Book Table al lado del logo
            Row(
                modifier = Modifier.padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF1F3F4),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Text("Follow", fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = { },
                    modifier = Modifier.height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF4A3D)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Text("Book Table", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun RestaurantInfoSection(restaurant: Restaurant) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = restaurant.name,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "⭐ ${restaurant.rating}",
                color = Color(0xFFFF4A3D),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = " • ${restaurant.cuisine}",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = restaurant.description,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ActionTabsBar() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabIcons = listOf(
        Icons.Default.GridView,
        Icons.Default.RestaurantMenu,
        Icons.Default.Stars,
        Icons.Default.Info
    )

    Column {
        Row(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabIcons.forEachIndexed { index, icon ->
                val isSelected = selectedTab == index
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedTab = index }
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isSelected) Color(0xFFFF4A3D) else Color.LightGray,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (isSelected) {
                        // La línea roja indicadora
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(3.dp)
                                .background(Color(0xFFFF4A3D))
                        )
                    } else {
                        Spacer(modifier = Modifier.height(3.dp))
                    }
                }
            }
        }
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = { Text(sampleRestaurant.name, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Share, contentDescription = "Share")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun RestaurantProfileScreenPreview() {
    MaterialTheme {
        RestaurantProfileScreen()
    }
}
