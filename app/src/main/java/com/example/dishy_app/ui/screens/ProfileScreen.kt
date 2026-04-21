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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AddAPhoto
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dishy_app.FirebaseAuthManager
import com.example.dishy_app.ui.components.BottomBarComponent

@Composable
fun ProfileScreen(navController: NavController) {
    var selectedTab by remember { mutableIntStateOf(0) }

    // Obtener datos del usuario real desde Firebase
    val currentUser by FirebaseAuthManager.currentUser.collectAsState()
    val userName = currentUser?.displayName ?: "User Name"
    val userEmail = currentUser?.email ?: "user@example.com"
    val userPhoto = currentUser?.photoUrl?.toString() ?: "https://www.w3schools.com/howto/img_avatar.png"

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
                .background(Color(0xFFF8F8F8))
        ) {
            // --- HEADER CON GRADIENTE ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFFE0E0E0), Color.White)
                            )
                        )
                )

                // Icono de Ajustes
                IconButton(
                    onClick = { /* Settings */ },
                    modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
                ) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }

                // Info del Perfil
                Column(
                    modifier = Modifier.align(Alignment.Center).padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box {
                        AsyncImage(
                            model = userPhoto,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.White, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFF4A3D))
                                .align(Alignment.BottomEnd)
                                .border(2.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Edit, "", tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                    }

                    Text(
                        text = userName,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = userEmail,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // --- BOTÓN EDIT PROFILE ---
            Button(
                onClick = { /* Acción */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4A3D)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Edit Profile", color = Color.White)
            }

            // --- ESTADÍSTICAS ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("42", "Places")
                StatItem("158", "Photos")
                StatItem("24", "Reviews")
            }

            // --- TABS ---
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = Color(0xFFFF4A3D),
                indicator = { tabPositions ->
                    if (selectedTab < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Color(0xFFFF4A3D)
                        )
                    }
                },
                divider = {}
            ) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text("My Vibes", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.SemiBold)
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("Visited History", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.SemiBold)
                }
            }

            // --- GRID DE IMÁGENES ---
            val photoList = listOf("p1", "p2", "p3", "p4", "p5")
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(photoList) { _ ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(0.8f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.LightGray)
                    ) {
                        AsyncImage(
                            model = "https://via.placeholder.com/300",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .aspectRatio(0.8f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFE9EEF3))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Outlined.AddAPhoto, null, tint = Color.Gray)
                            Text("Add New", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(number: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = number, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF4A3D))
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
    }
}
