package com.example.dishy_app.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dishy_app.FirebaseAuthManager
import com.example.dishy_app.ui.viewModel.CreatePostViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    imageUri: String?, 
    navController: NavController,
    viewModel: CreatePostViewModel = viewModel()
) {
    // ESTADOS PARA LOS ATRIBUTOS DEL MODELO
    var caption by remember { mutableStateOf("") }
    var placeName by remember { mutableStateOf("") }
    var locationName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Cafes") }
    var selectedRating by remember { mutableDoubleStateOf(5.0) }
    
    var selectedWifi by remember { mutableStateOf("Average") }
    var selectedComfort by remember { mutableStateOf("Lounge") }
    var selectedNoise by remember { mutableStateOf("Silent") }
    var hasPlugs by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val currentUser by FirebaseAuthManager.currentUser.collectAsState()
    
    // Placeholder predeterminado si no hay foto
    val defaultAvatar = "https://cdn-icons-png.flaticon.com/512/149/149071.png"
    val userPhoto = currentUser?.photoUrl?.toString() ?: defaultAvatar

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Share Vibe", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Close, null)
                    }
                },
                actions = {
                    if (viewModel.isUploading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp).padding(end = 16.dp),
                            color = Color(0xFFFF4A3D),
                            strokeWidth = 2.dp
                        )
                    } else {
                        TextButton(onClick = { 
                            if (!imageUri.isNullOrBlank() && placeName.isNotBlank()) {
                                scope.launch {
                                    viewModel.uploadAndCreatePost(
                                        imageUri = imageUri.toUri(),
                                        caption = caption,
                                        placeName = placeName,
                                        location = locationName,
                                        category = selectedCategory,
                                        rating = selectedRating,
                                        wifi = selectedWifi,
                                        comfort = selectedComfort,
                                        noise = selectedNoise,
                                        plugs = hasPlugs,
                                        onSuccess = {
                                            Toast.makeText(context, "Vibe shared!", Toast.LENGTH_SHORT).show()
                                            navController.navigate("home") {
                                                popUpTo("home") { inclusive = true }
                                            }
                                        },
                                        onError = { error ->
                                            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                                        }
                                    )
                                }
                            } else {
                                Toast.makeText(context, "Please enter place name", Toast.LENGTH_SHORT).show()
                            }
                        }) {
                            Text("Share", color = Color(0xFFFF4A3D), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            // Imagen Preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF1F3F4))
            ) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Datos del Lugar (Crucial para el modelo Place/Post)
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                OutlinedTextField(
                    value = placeName,
                    onValueChange = { placeName = it },
                    label = { Text("Where are you?") },
                    placeholder = { Text("e.g. Starbucks, La Fogata...") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Storefront, null, tint = Color(0xFFFF4A3D)) },
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = locationName,
                    onValueChange = { locationName = it },
                    label = { Text("City / Area") },
                    placeholder = { Text("e.g. Armenia, Quindío") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.LocationOn, null, tint = Color(0xFFFF4A3D)) },
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Categoría y Calificación (Atributos de Empresa)
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var expanded by remember { mutableStateOf(false) }
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(selectedCategory, color = Color.Black)
                        Icon(Icons.Default.ArrowDropDown, null, tint = Color.Gray)
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        listOf("Cafes", "Restaurants", "Workspaces", "Bars").forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = { 
                                    selectedCategory = cat
                                    expanded = false 
                                }
                            )
                        }
                    }
                }
                
                OutlinedButton(
                    onClick = { 
                        if(selectedRating < 5.0) selectedRating += 0.5 else selectedRating = 1.0 
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("$selectedRating Rating", color = Color.Black)
                }
            }

            HorizontalDivider(modifier = Modifier.padding(16.dp), thickness = 0.5.dp)

            // Caption Estilo Red Social
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = userPhoto,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                TextField(
                    value = caption,
                    onValueChange = { caption = it },
                    placeholder = { Text("How is the vibe? (Caption)", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            Text("Vibe Attributes", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

            VibeSelectorGroup(title = "Wi-Fi Speed", options = listOf("None", "Average", "High Speed"), selected = selectedWifi, onSelect = { selectedWifi = it })
            VibeSelectorGroup(title = "Comfort", options = listOf("Stools", "Chairs", "Lounge"), selected = selectedComfort, onSelect = { selectedComfort = it })
            VibeSelectorGroup(title = "Noise", options = listOf("Silent", "Chatty", "Loud"), selected = selectedNoise, onSelect = { selectedNoise = it })
            
            // Switch para Enchufes (Atributo de modelo VibeSpecs)
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Power, null, tint = if(hasPlugs) Color(0xFFFF4A3D) else Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Power Plugs Available", fontSize = 14.sp)
                }
                Switch(
                    checked = hasPlugs, 
                    onCheckedChange = { hasPlugs = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFFF4A3D))
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun VibeSelectorGroup(title: String, options: List<String>, selected: String, onSelect: (String) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(text = title, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                val isSelected = selected == option
                Surface(
                    modifier = Modifier.weight(1f).clickable { onSelect(option) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Color(0xFFFF4A3D) else Color(0xFFF1F3F4),
                    border = if (isSelected) null else androidx.compose.foundation.BorderStroke(0.5.dp, Color.LightGray)
                ) {
                    Text(
                        text = option,
                        modifier = Modifier.padding(vertical = 10.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = if (isSelected) Color.White else Color.DarkGray,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
