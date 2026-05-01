package com.example.dishy_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostDetailScreen(post: DishyPost, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        // Imagen con Overlay de Nombre y Ubicación
        Box(modifier = Modifier.fillMaxWidth().height(400.dp)) {
            AsyncImage(
                model = post.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Botón volver
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .background(Color(0x99000000))
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(
                        androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                        )
                    )
                    .padding(20.dp)
            ) {
                Text(post.placeName, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, null, tint = Color(0xFFFF4A3D), modifier = Modifier.size(14.dp))
                    Text(post.location, color = Color.White.copy(alpha = 0.8f), fontSize = 16.sp)
                }
            }
        }

        // Fichas de Vibe (WiFi, Comfort, Noise)
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            VibeDisplayCard(Icons.Default.Wifi, post.vibeSpecs.wifiSpeed, "WIFI SPEED", Modifier.weight(1f))
            VibeDisplayCard(Icons.Default.Chair, post.vibeSpecs.comfortLevel, "COMFORT", Modifier.weight(1f))
            VibeDisplayCard(Icons.AutoMirrored.Filled.VolumeUp, post.vibeSpecs.noiseLevel, "NOISE", Modifier.weight(1f))
        }

        // Info del Autor y Descripción
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = post.authorPhotoUrl,
                contentDescription = null,
                modifier = Modifier.size(45.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(post.authorName, fontWeight = FontWeight.Bold)
                    if (post.authorRole == "BUSINESS") {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.CheckCircle, null, tint = Color(0xFFFF4A3D), modifier = Modifier.size(14.dp))
                    }
                }
                Text("Posted 2 hours ago", color = Color.Gray, fontSize = 12.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4A3D)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Follow", fontWeight = FontWeight.Bold)
            }
        }

        Text(
            text = post.description,
            modifier = Modifier.padding(horizontal = 16.dp),
            lineHeight = 22.sp,
            fontSize = 15.sp,
            color = Color.DarkGray
        )
        
        // Tags
        FlowRow(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            post.tags.forEach { tag ->
                Text("#$tag", color = Color(0xFFFF4A3D), fontWeight = FontWeight.Medium)
            }
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4A3D))
        ) {
            Icon(Icons.Default.Directions, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Get Directions", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun VibeDisplayCard(icon: ImageVector, value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, null, tint = Color(0xFFFF4A3D), modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(label, fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailScreenPreview() {
    PostDetailScreen(post = samplePosts.first(), navController = rememberNavController())
}
