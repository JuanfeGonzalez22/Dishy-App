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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dishy_app.FirebaseAuthManager
import com.example.dishy_app.ui.components.BottomBarComponent
import com.example.dishy_app.ui.components.StatItem
import com.example.dishy_app.ui.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantProfileScreen(
    navController: NavController,
    targetUserId: String? = null,
    homeViewModel: HomeViewModel = viewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    val currentUserId = FirebaseAuthManager.currentUser.collectAsState().value?.uid
    val userRole by FirebaseAuthManager.userRole.collectAsState()
    
    val isMyProfile = targetUserId == null || targetUserId == currentUserId
    val canEdit = isMyProfile || userRole == "ADMIN"
    
    val currentUserName by FirebaseAuthManager.userName.collectAsState()
    val currentUser by FirebaseAuthManager.currentUser.collectAsState()
    
    val defaultAvatar = "https://cdn-icons-png.flaticon.com/512/149/149071.png"
    val businessName = if (isMyProfile) (currentUserName ?: "My Business") else "Restaurant Name"
    val businessPhoto = if (isMyProfile) (currentUser?.photoUrl?.toString() ?: defaultAvatar) else defaultAvatar

    val effectiveUserId = targetUserId ?: currentUserId ?: ""
    val restaurantPosts = homeViewModel.posts.filter { it.userId == effectiveUserId }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (isMyProfile) "Business Hub" else "Restaurant", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            if (isMyProfile) {
                BottomBarComponent(
                    currentRoute = "profile",
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .padding(4.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(Color(0xFFFF4A3D).copy(0.2f), Color.Transparent)
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = businessPhoto,
                            contentDescription = "Restaurant Logo",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(3.dp, Color.White, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFFF4A3D),
                        modifier = Modifier.size(24.dp).offset(x = (-4).dp, y = (-4).dp).border(2.dp, Color.White, CircleShape)
                    ) {
                        Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.padding(4.dp))
                    }
                }

                Text(businessName, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2D2D2D))
                Text("Business Member", fontSize = 13.sp, color = Color.Gray)
                
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Icon(Icons.Default.LocationOn, null, tint = Color(0xFFFF4A3D), modifier = Modifier.size(14.dp))
                    Text(" Location Enabled", fontSize = 12.sp, color = Color(0xFFFF4A3D), fontWeight = FontWeight.Bold)
                }

                if (canEdit) {
                    Row(modifier = Modifier.fillMaxWidth().padding(24.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(
                            onClick = { navController.navigate("edit_profile/$effectiveUserId") }, 
                            modifier = Modifier.weight(1f).height(48.dp), 
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4A3D)), 
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Edit Profile", fontWeight = FontWeight.Bold)
                        }
                        if (isMyProfile) {
                            Button(onClick = { }, modifier = Modifier.weight(1f).height(48.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F3F4)), shape = RoundedCornerShape(16.dp)) {
                                Text("Manage", color = Color(0xFFFF4A3D), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else {
                    Button(onClick = { }, modifier = Modifier.fillMaxWidth().padding(24.dp).height(48.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4A3D)), shape = RoundedCornerShape(16.dp)) {
                        Text("Follow", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatItem("0", "FOLLOWERS")
                StatItem(restaurantPosts.size.toString(), "POSTS")
                StatItem("0 ★", "RATING")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = Color(0xFFFF4A3D),
                indicator = { tabPositions ->
                    if (selectedTab < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Color(0xFFFF4A3D)
                        )
                    }
                }
            ) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text("Our Feed", modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Bold)
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("Tagged", modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Bold)
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(1.dp),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                if (isMyProfile) {
                    item {
                        Box(modifier = Modifier.aspectRatio(1f).background(Color(0xFFF1F3F4)).clickable { navController.navigate("camera") }, contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.AddAPhoto, null, tint = Color.Gray, modifier = Modifier.size(24.dp))
                                Text("Add Vibe", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                items(restaurantPosts) { post ->
                    AsyncImage(
                        model = post.imageUrl,
                        contentDescription = null,
                        modifier = Modifier.aspectRatio(1f).clickable { navController.navigate("post_detail/${post.id}") },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
