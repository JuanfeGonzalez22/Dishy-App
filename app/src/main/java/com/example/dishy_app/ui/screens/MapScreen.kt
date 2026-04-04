package com.example.dishy_app.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dishy_app.ui.viewModel.MapViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.compose.runtime.collectAsState
import coil.compose.AsyncImage

@Composable
fun MapScreen(navController: NavController) {

    val context = LocalContext.current
    val viewModel: MapViewModel = viewModel()
    val locationGranted by viewModel.locationGranted.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") }

    // Launcher para pedir permiso de ubicacion
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.onPermissionResult(isGranted)
    }

    // Al abrir la pantalla verificamos el permiso
    LaunchedEffect(Unit) {
        viewModel.checkLocationPermission()
        if (!locationGranted) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // ---- EL MAPA ----
        AndroidView(
            factory = { ctx ->
                // Configuramos osmdroid
                Configuration.getInstance().load(
                    ctx,
                    ctx.getSharedPreferences("osmdroid", 0)
                )

                // Creamos el MapView
                MapView(ctx).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)

                    // Centramos en Armenia, Colombia
                    val startPoint = GeoPoint(4.5339, -75.6811)
                    controller.setZoom(15.0)
                    controller.setCenter(startPoint)

                    // Marcadores de cada lugar
                    val coordenadas = listOf(
                        Triple("The Coffee Collective", 4.5339, -75.6811),
                        Triple("Nomad Workspace", 4.5350, -75.6820),
                        Triple("Bluebird Bistro", 4.5360, -75.6800)
                    )
                    coordenadas.forEach { (nombre, lat, lng) ->
                        val marker = Marker(this)
                        marker.position = GeoPoint(lat, lng)
                        marker.title = nombre
                        overlays.add(marker)
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // ---- TOP BAR CON BUSQUEDA ----
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            // Barra de busqueda
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                shape = RoundedCornerShape(30.dp),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Search Vibe",
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Filtros",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificaciones",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Fila de filtros
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val filtros = listOf("Open Now", "Fast Wi-Fi", "Quiet", "Cafes", "Workspaces")
                items(filtros) { filtro ->
                    FilterChip(
                        selected = selectedFilter == filtro,
                        onClick = { selectedFilter = filtro },
                        label = { Text(filtro, fontSize = 12.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = when (filtro) {
                                    "Open Now" -> Icons.Default.Schedule
                                    "Fast Wi-Fi" -> Icons.Default.Wifi
                                    "Quiet" -> Icons.Default.VolumeOff
                                    "Cafes" -> Icons.Default.Coffee
                                    else -> Icons.Default.Work
                                },
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFFF4A3D),
                            selectedLabelColor = Color.White,
                            selectedLeadingIconColor = Color.White
                        )
                    )
                }
            }
        }

        // ---- CARDS DE LUGARES EN LA PARTE INFERIOR ----
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(samplePlaces) { place ->
                PlaceMapCard(
                    place = place,
                    onClick = { navController.navigate("detail/${place.id}") }
                )
            }
        }

        // ---- BARRA DE NAVEGACION INFERIOR ----
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NavigationItem(Icons.Default.Home, "Home", selected = false)
                    NavigationItem(Icons.Default.Public, "Map", selected = true)
                    Spacer(modifier = Modifier.width(48.dp))
                    NavigationItem(Icons.Default.FavoriteBorder, "Saved", selected = false)
                    NavigationItem(Icons.Default.Person, "Profile", selected = false)
                }
            }

            // FAB del dado
            FloatingActionButton(
                onClick = { navController.navigate("shake") },
                containerColor = Color(0xFFFF4A3D),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-28).dp)
                    .size(56.dp)
            ) {
                Icon(Icons.Default.Casino, "Random", Modifier.size(28.dp))
            }
        }
    }
}

// ---- CARD PEQUEÑA QUE APARECE SOBRE EL MAPA ----
@Composable
fun PlaceMapCard(place: Place, onClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .width(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 6.dp
    ) {
        Column {
            // Imagen del lugar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                AsyncImage(
                    model = place.imageUrl,
                    contentDescription = place.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Badge POPULAR
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFFF4A3D))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "POPULAR",
                        fontSize = 9.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                // Rating
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⭐", fontSize = 10.sp)
                        Text(
                            text = " ${place.rating}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Info del lugar
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = place.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Cafe • ${place.distance} • \$\$",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFF0F0F0))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(place.description, fontSize = 10.sp, color = Color.DarkGray)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFF0F0F0))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("Good Wifi", fontSize = 10.sp, color = Color.DarkGray)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapScreenPreview() {
    MaterialTheme {
        MapScreen(navController = rememberNavController())
    }
}