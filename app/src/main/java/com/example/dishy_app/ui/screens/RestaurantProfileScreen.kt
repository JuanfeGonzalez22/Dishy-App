package com.example.dishy_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.dishy_app.FirebaseAuthManager
import com.example.dishy_app.ui.components.BottomBarComponent

@Composable
fun RestaurantProfileScreen(navController: NavController) {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    // Obtener datos reales del usuario desde nuestro Manager
    val currentUserName by FirebaseAuthManager.userName.collectAsState()
    val currentUser by FirebaseAuthManager.currentUser.collectAsState()
    
    val businessName = currentUserName ?: "Cargando..."
    val businessPhoto = currentUser?.photoUrl?.toString() ?: "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4"

    Scaffold(
        bottomBar = {
            BottomBarComponent(
                currentRoute = "profile",
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
                .background(Color.White)
        ) {
            // --- TOP BAR "Business Hub" ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Storefront,
                        contentDescription = null,
                        tint = Color(0xFFFF4A3D),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Business Hub",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF2D2D2D)
                    )
                }
                Row {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFFFF1F0),
                        modifier = Modifier.size(40.dp)
                    ) {
                        IconButton(onClick = { }) {
                            Icon(Icons.AutoMirrored.Outlined.ShowChart, null, tint = Color(0xFFFF4A3D), modifier = Modifier.size(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFFFF1F0),
                        modifier = Modifier.size(40.dp)
                    ) {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Settings, null, tint = Color(0xFFFF4A3D), modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            // --- PROFILE HEADER ---
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    // Profile Image with Glow Effect
                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .padding(8.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(Color(0xFFCC9933), Color.Transparent)
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = businessPhoto,
                            contentDescription = "Restaurant Logo",
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape)
                                .border(3.dp, Color.White, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    // Verified Badge
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFFF4A3D),
                        modifier = Modifier
                            .size(28.dp)
                            .offset(x = (-8).dp, y = (-8).dp)
                            .border(2.dp, Color.White, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Verified",
                            tint = Color.White,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }

                Text(
                    text = businessName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D2D2D)
                )
                Text(
                    text = "Fine Dining & Mediterranean Fusion",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(Icons.Default.LocationOn, null, tint = Color(0xFFFF4A3D), modifier = Modifier.size(14.dp))
                    Text(text = " MADRID, SPAIN", fontSize = 12.sp, color = Color(0xFFFF4A3D), fontWeight = FontWeight.Bold)
                }

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f).height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4A3D)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Edit Profile", fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f).height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F3F4)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Manage Business", color = Color(0xFFFF4A3D), fontWeight = FontWeight.Bold)
                    }
                }
            }

            // --- STATS CARDS ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard("12.5k", "FOLLOWERS", Modifier.weight(1f))
                StatCard("842", "TOTAL VIBES", Modifier.weight(1f))
                StatCard("4.9 ★", "AVG RATING", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- TABS ---
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = Color(0xFFFF4A3D),
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color(0xFFFF4A3D)
                    )
                },
                divider = { HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray) }
            ) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text("Our Feed", modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Bold, color = if (selectedTab == 0) Color(0xFFFF4A3D) else Color.Gray)
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("Tagged", modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Bold, color = if (selectedTab == 1) Color(0xFFFF4A3D) else Color.Gray)
                }
            }

            // --- PHOTO GRID ---
            val photos = List(9) { "https://picsum.photos/400/400?random=$it" }
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(photos) { photoUrl ->
                    AsyncImage(
                        model = photoUrl,
                        contentDescription = null,
                        modifier = Modifier.aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFFF4A3D))
            Text(text = label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantProfileScreenPreview() {
    RestaurantProfileScreen(navController = rememberNavController())
}
