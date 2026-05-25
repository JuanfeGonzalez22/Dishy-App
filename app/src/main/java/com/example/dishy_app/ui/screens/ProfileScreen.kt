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
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Composable
fun ProfileScreen(navController: NavController, userId: String? = null) {
    val currentUser by FirebaseAuthManager.currentUser.collectAsState()
    val userRole by FirebaseAuthManager.userRole.collectAsState()
    val currentUserId = currentUser?.uid
    
    val isMyProfile = userId == null || userId == currentUserId
    val canEdit = isMyProfile || userRole == "ADMIN"

    if (isMyProfile && userRole == "BUSINESS") {
        RestaurantProfileScreen(navController = navController)
    } else {
        UserProfileScreen(
            navController = navController, 
            isMyProfile = isMyProfile, 
            canEdit = canEdit,
            targetUserId = userId ?: currentUserId ?: ""
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    navController: NavController, 
    isMyProfile: Boolean, 
    canEdit: Boolean,
    targetUserId: String,
    homeViewModel: HomeViewModel = viewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val currentUser by FirebaseAuthManager.currentUser.collectAsState()
    
    val defaultAvatar = "https://cdn-icons-png.flaticon.com/512/149/149071.png"
    val userPosts = homeViewModel.posts.filter { it.userId == targetUserId }

    val userName = if (isMyProfile) (currentUser?.displayName ?: "User Name") else "Member Profile"
    val userPhoto = if (isMyProfile) (currentUser?.photoUrl?.toString() ?: defaultAvatar) else defaultAvatar

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (isMyProfile) "My Profile" else "Profile", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
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
                .background(Color(0xFFFBFBFB))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = userPhoto,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp).clip(CircleShape).border(3.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(userName, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp))
                Text("Vibe Explorer", fontSize = 13.sp, color = Color.Gray)
            }

            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatItem(userPosts.size.toString(), "Posts")
                StatItem("0", "Following")
                StatItem("0", "Followers")
            }

            if (canEdit) {
                Button(
                    onClick = { navController.navigate("edit_profile/$targetUserId") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4A3D)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Edit Profile", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
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
                    Text("Vibes", modifier = Modifier.padding(14.dp), fontWeight = FontWeight.Bold)
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("Saved", modifier = Modifier.padding(14.dp), fontWeight = FontWeight.Bold)
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(1.dp),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp),
                modifier = Modifier.weight(1f)
            ) {
                if (isMyProfile) {
                    item {
                        Box(
                            modifier = Modifier.aspectRatio(1f).background(Color(0xFFF1F3F4)).clickable { navController.navigate("camera") },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Outlined.AddAPhoto, null, tint = Color.Gray, modifier = Modifier.size(24.dp))
                                Text("Add Vibe", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                items(userPosts) { post ->
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
