package com.example.dishy_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dishy_app.ui.components.BottomBarComponent
import com.example.dishy_app.ui.viewModel.PlaceDetailViewModel

@Composable
fun PlaceDetailScreen(
    placeId: String, // Cambiado de Int a String para Firebase
    navController: NavController,
    viewModel: PlaceDetailViewModel = viewModel()
) {
    // Cargamos el lugar al iniciar la pantalla
    LaunchedEffect(placeId) {
        viewModel.loadPlace(placeId)
    }

    val place = viewModel.place
    val scrollState = rememberScrollState()

    if (place == null) {
        // Pantalla de carga simple
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFFFF4A3D))
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(bottom = 80.dp)
            ) {
                // Imagen Hero
                Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
                    AsyncImage(
                        model = place.imageUrl,
                        contentDescription = place.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopStart)
                            .clip(CircleShape)
                            .background(Color(0x99000000))
                    ) {
                        Icon(Icons.Default.ArrowBack, "Volver", tint = Color.White)
                    }
                }

                // Detalles
                Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                    Text(place.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⭐ ${place.rating}", fontWeight = FontWeight.Bold, color = Color(0xFFFFB300))
                        Text(" (${place.reviews} reviews) • ${place.category}", color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(place.description, fontSize = 14.sp, color = Color.DarkGray, lineHeight = 22.sp)

                    Spacer(modifier = Modifier.height(20.dp))

                    // Vibes Section
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        VibeChip(Icons.Default.Wifi, "Fast", "WIFI", Modifier.weight(1f))
                        VibeChip(Icons.Default.Power, "Available", "PLUGS", Modifier.weight(1f))
                        VibeChip(Icons.Default.VolumeDown, "Quiet", "NOISE", Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Community Photos", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        place.communityPhotos.forEach { photoUrl ->
                            AsyncImage(
                                model = photoUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.weight(1f).height(100.dp).clip(RoundedCornerShape(12.dp))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                    
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4A3D)),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Icon(Icons.Default.Directions, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Get Directions", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                BottomBarComponent(
                    currentRoute = "",
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
        }
    }
}

@Composable
fun VibeChip(icon: ImageVector, value: String, label: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5)
    ) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = Color(0xFF555555), modifier = Modifier.size(20.dp))
            Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(label, fontSize = 9.sp, color = Color.Gray)
        }
    }
}
