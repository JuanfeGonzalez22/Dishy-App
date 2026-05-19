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
    var caption by remember { mutableStateOf("") }
    var selectedWifi by remember { mutableStateOf("Average") }
    var selectedComfort by remember { mutableStateOf("Lounge") }
    var selectedNoise by remember { mutableStateOf("Silent") }
    
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val currentUser by FirebaseAuthManager.currentUser.collectAsState()
    val userPhoto = currentUser?.photoUrl?.toString() ?: "https://i.pravatar.cc/150"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("New Post", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
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
                            if (!imageUri.isNullOrBlank()) {
                                scope.launch {
                                    viewModel.uploadAndCreatePost(
                                        imageUri = Uri.parse(imageUri),
                                        caption = caption,
                                        wifi = selectedWifi,
                                        comfort = selectedComfort,
                                        noise = selectedNoise,
                                        onSuccess = {
                                            Toast.makeText(context, "Post shared successfully!", Toast.LENGTH_SHORT).show()
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
                                Toast.makeText(context, "Error: No image URI found", Toast.LENGTH_SHORT).show()
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
                .background(Color(0xFFFBFBFB))
        ) {
            // Preview de Imagen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)
            ) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Caption
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = userPhoto,
                    contentDescription = null,
                    modifier = Modifier.size(45.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                TextField(
                    value = caption,
                    onValueChange = { caption = it },
                    placeholder = { Text("Write a caption...", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
            
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)

            Text("Vibe Specifics", fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))

            VibeSelectorGroup(
                title = "Wi-Fi Speed",
                options = listOf("Dial-up", "Average", "High Speed"),
                selected = selectedWifi,
                onSelect = { selectedWifi = it }
            )

            VibeSelectorGroup(
                title = "Comfort Level",
                options = listOf("Stools", "Chairs", "Lounge"),
                selected = selectedComfort,
                onSelect = { selectedComfort = it }
            )

            VibeSelectorGroup(
                title = "Noise Level",
                options = listOf("Silent", "Chatty", "Loud"),
                selected = selectedNoise,
                onSelect = { selectedNoise = it }
            )
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun VibeSelectorGroup(title: String, options: List<String>, selected: String, onSelect: (String) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(text = title, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
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
                    color = if (isSelected) Color(0xFFFF4A3D) else Color(0xFFF1F3F4)
                ) {
                    Text(
                        text = option,
                        modifier = Modifier.padding(vertical = 10.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = if (isSelected) Color.White else Color.DarkGray,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
