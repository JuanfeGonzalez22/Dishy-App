package com.example.dishy_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dishy_app.ui.theme.DishyAppTheme

@Composable
fun LoginScreen() {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Card(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {


                Spacer(modifier = Modifier.height(46.dp))

                Text(
                    text = "Dishy",
                    fontSize = 28.sp,
                    color = Color(0xFFFF4A3D),
                    fontWeight = FontWeight.Bold

                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Welcome back to Dishy",
                    fontSize = 28.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center,  
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Discover what's happening around you",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold

                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text(text = "Email")
                            },
                    placeholder = {
                        Text(text = "Enter your Email")

                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)

                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text(text = "Password")
                    },
                    placeholder = {
                        Text(text = "Enter your Password")

                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)

                )
                
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding( 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {}) {
                        Text(
                            text = "Forgot Password?",
                            color = Color(0xFFFF4A3D)
                        )
                    }
                }


                Spacer(modifier = Modifier.height(8.dp))


                // Botón de inicio de sesión
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4A3D)),
                    shape = RoundedCornerShape(24.dp),

                ) {
                    Text(
                        text = "Log In",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }


                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )

                    Text(
                        text = "Or continue with",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

                    Divider(
                        modifier = Modifier.weight(1f),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                }


                Spacer(modifier = Modifier.height(32.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Botón de Google
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                            .height(48.dp).padding(end = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Google",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Google")
                    }

                    // Botón de Apple
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                            .height(48.dp)
                            .padding(start = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Apple",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Apple")
                    }
                }


                Spacer(modifier = Modifier.height(46.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account? ",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { }) {
                        Text(
                            text = "Sign Up",
                            color = Color(0xFFFF4A3D),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }

        }
    }



}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    DishyAppTheme{
        LoginScreen()
    }
}