package com.example.dishy_app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
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
import com.example.dishy_app.ui.viewModel.EditProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    userId: String,
    navController: NavController,
    viewModel: EditProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val defaultAvatar = "https://cdn-icons-png.flaticon.com/512/149/149071.png"

    LaunchedEffect(userId) {
        viewModel.loadUserProfile(userId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Profile", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    if (viewModel.isSaving) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp).padding(end = 16.dp), color = Color(0xFFFF4A3D))
                    } else {
                        TextButton(onClick = {
                            viewModel.saveProfile(userId) {
                                Toast.makeText(context, "Changes saved!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        }) {
                            Text("Save", color = Color(0xFFFF4A3D), fontWeight = FontWeight.Bold)
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        if (viewModel.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFFF4A3D))
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // FOTO DE PERFIL
                Box(contentAlignment = Alignment.BottomEnd) {
                    AsyncImage(
                        model = if (viewModel.photoUrl.isBlank()) defaultAvatar else viewModel.photoUrl,
                        contentDescription = null,
                        modifier = Modifier.size(110.dp).clip(CircleShape).border(2.dp, Color.LightGray, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Surface(color = Color(0xFFFF4A3D), shape = CircleShape, modifier = Modifier.size(30.dp)) {
                        Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.padding(6.dp))
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // CAMPO NOMBRE
                OutlinedTextField(
                    value = viewModel.name,
                    onValueChange = { viewModel.name = it },
                    label = { Text("Display Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.Person, null, tint = Color.Gray) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // CAMPO UBICACIÓN
                OutlinedTextField(
                    value = viewModel.location,
                    onValueChange = { viewModel.location = it },
                    label = { Text("City / Location") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.LocationOn, null, tint = Color.Gray) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // CAMPO BIO / DESCRIPCIÓN
                OutlinedTextField(
                    value = viewModel.bio,
                    onValueChange = { viewModel.bio = it },
                    label = { Text("Bio / Description") },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("Tell us about your vibes...") }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // SECCIÓN ADMIN (Solo si el editor es Admin)
                if (viewModel.currentUserRole == "ADMIN") {
                    Text("Admin Controls", fontWeight = FontWeight.Bold, color = Color(0xFFFF4A3D), modifier = Modifier.align(Alignment.Start))
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    var expanded by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                            Icon(Icons.Default.Badge, null, tint = Color.Gray)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Account Role: ${viewModel.role}", color = Color.Black)
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            listOf("USER", "BUSINESS", "ADMIN").forEach { r ->
                                DropdownMenuItem(text = { Text(r) }, onClick = { viewModel.role = r; expanded = false })
                            }
                        }
                    }
                } else {
                    // Si no es admin, solo mostramos el tipo de cuenta pero bloqueado
                    OutlinedTextField(
                        value = viewModel.role,
                        onValueChange = {},
                        label = { Text("Account Type") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(disabledContainerColor = Color(0xFFF8F8F8))
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
                Text("Security Note: Email changes are disabled for security reasons.", fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}
