package com.example.dishy_app.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
@Composable
fun ShakeDiscoverScreen(navController: NavController){

    //Lugar aleatorio de la lista(de momento)
    val randomPlace = samplePlaces.random()

    //Animacion del circulo
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    val scaleOuter by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scaleOuter"
    )

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Volver",
                        modifier = Modifier
                            .scale(-1f, 1f) // Esto hace que la flecha se mueva hacia la izquierda
                    )
                }
                Text(
                    text = "Shake to Discover",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = {  }){
                    Icon(
                        imageVector = Icons.Default.Help,
                        contentDescription = "Ayuda",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            //Circulo con animacion de pulso
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(260.dp)
            ){
                //Circulo exterior con blur simulado
                Box(
                    modifier = Modifier
                        .size(260.dp)
                        .scale(scale)
                        .clip(CircleShape)
                        .background(Color(0xFFFF4A3D).copy(alpha = 0.25f))
                )
                //Circulo interior
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .scale(scale)
                        .clip(CircleShape)
                        .background(Color(0xFFFF4A3D).copy(alpha = 0.25f))
                )
                //Circulo principal rojo
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFF4A3D)),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Default.Casino,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))

            // Texto
            Text(
                text = "Shake your phone!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}