package com.example.dishy_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomBarComponent(
    currentRoute: String,
    onNavigate: (String) -> Unit) {


    BottomAppBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            NavigationItem(
                icon = Icons.Default.Home,
                label = "Home",
                selected = currentRoute == "home",
                onClick = { onNavigate("home") }
            )

            NavigationItem(
                icon = Icons.Default.Public,
                label = "Map",
                selected = currentRoute == "map",
                onClick = { onNavigate("map") }
            )

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .offset(y = (-10).dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF4A3D))
                    .clickable { onNavigate("shake") },
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    Icons.Default.Casino,
                    null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )

            }

            NavigationItem(
                icon = Icons.Default.FavoriteBorder,
                label = "Saved",
                selected = currentRoute == "saved_places",
                onClick = { onNavigate("saved_places") }
            )

            NavigationItem(
                icon = Icons.Default.Person,
                label = "Profile",
                selected = currentRoute == "profile",
                onClick = { onNavigate("profile") }
            )

        }
    }

}
