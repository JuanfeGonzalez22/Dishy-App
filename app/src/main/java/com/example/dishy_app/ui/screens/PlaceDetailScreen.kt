package com.example.dishy_app.ui.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.VolumeDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun PlaceDetailScreen(place: Place, navController: NavController) {
    val scrollState = rememberScrollState()

    // Usamos un Box para que la barra inferior se quede fija abajo
    Box(modifier = Modifier.fillMaxSize()) {

        // 1. Contenido con Scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 80.dp) // Espacio para que el contenido no quede oculto tras la barra
        ) {
            // Imagen Hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                Image(
                    painter = painterResource(id = place.imageRes),
                    contentDescription = place.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
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
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }

                // Botones share y favoritos
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0x99000000))
                    ) {
                        Icon(
                            imageVector = Icons.Default.IosShare,
                            contentDescription = "Compartir",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0x99000000))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorito",
                            tint = Color.White
                        )
                    }
                }

                // Badge OPEN NOW
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF2E7D32))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "OPEN NOW",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Contenido de texto y detalles
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = place.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "⭐️", fontSize = 14.sp)
                    Text(
                        text = " (${place.reviews} reviews) • Cafe • \$\$",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "A quiet spot perfect for deep work with high-speed internet and plenty of power outlets.",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    VibeChip(Icons.Default.Wifi, "100 Mbps", "SPEED", Modifier.weight(1f))
                    VibeChip(Icons.Default.PowerSettingsNew, "Plenty", "OUTLETS", Modifier.weight(1f))
                    VibeChip(Icons.Default.VolumeDown, "Low Noise", "QUIET", Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Comunidad
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Community", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "See All",
                        fontSize = 14.sp,
                        color = Color(0xFFFF4A3D),
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    place.communityPhotos.forEachIndexed { index, photoRes ->
                        Image(
                            painter = painterResource(id = photoRes),
                            contentDescription = "Photo $index",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .weight(1f)
                                .height(80.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4A3D)),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Icon(Icons.Default.IosShare, null, Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Get Directions", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // 2. Barra de Navegación Inferior (Fija al fondo)
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NavigationItem(Icons.Default.Home, "Explore", false)
                    NavigationItem(Icons.Default.Public, "Map", false)
                    Spacer(modifier = Modifier.width(48.dp)) // Espacio para el FAB
                    NavigationItem(Icons.Default.Favorite, "Saved", false)
                    NavigationItem(Icons.Default.Person, "Profile", false)
                }
            }

            // 3. El Dado (Floating Action Button) centrado sobre la barra
            FloatingActionButton(
                onClick = { },
                containerColor = Color(0xFFFF4A3D),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-28).dp) // Lo sube para que quede a mitad de la barra
                    .size(56.dp)
            ) {
                Icon(Icons.Default.Casino, "Random", Modifier.size(28.dp))
            }
        }
    }
}

@Composable
fun VibeChip(
    icon: ImageVector,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5),
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF555555),
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = label,
                fontSize = 10.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlaceDetailScreenPreview() {
    MaterialTheme {
        PlaceDetailScreen(
            place = samplePlaces.first(),
            navController = rememberNavController()
        )
    }
}

