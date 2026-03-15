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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun RegisterScreen() {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {


        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {


            Spacer(modifier = Modifier.height(88.dp))

            Text(
                text = "Creat Account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()

            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Join the Dishy Community ",
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(46.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(text = "Full Name")
                },
                placeholder = {
                    Text(text = "Write you full name")

                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)

            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(text = "Email")
                },
                placeholder = {
                    Text(text = "hello@Dishyapp.com")

                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)

            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(text = "Password")
                },
                placeholder = {
                    Text(text = "*********")

                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)

            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4A3D)),
                shape = RoundedCornerShape(24.dp),

                ) {
                Text(
                    text = "Sing up",
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

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Botón de Google
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Google",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Google",
                        color = Color.Black
                        )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Botón de Apple
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)

                    
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Apple",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Apple",
                        color = Color.White

                    )

                }

                Spacer(modifier = Modifier.height(46.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account? ",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { }) {
                        Text(
                            text = "Log in",
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
fun RegisterScreenPreview() {
    DishyAppTheme{
        RegisterScreen()
    }
}