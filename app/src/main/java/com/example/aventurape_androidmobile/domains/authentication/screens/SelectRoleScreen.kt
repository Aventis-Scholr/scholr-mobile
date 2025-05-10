package com.example.aventurape_androidmobile.domains.authentication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.domains.authentication.screens.viewModels.SignUpViewModel
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import com.example.aventurape_androidmobile.ui.theme.cabinFamily

// Definición de colores personalizados
private val PrimaryBrown = Color(0xFF765532)
private val LightBrown = Color(0xFF9B7B5B)
private val DarkBrown = Color(0xFF523A23)
private val BackgroundBrown = Color(0xFFF5E6D3)

@Composable
fun SelectRoleScreen(
    viewModel: SignUpViewModel,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBrown)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo con sombra y animación
            Box(
                modifier = Modifier
                    .size(200.dp) // Define el tamaño del círculo (ancho y alto deben ser iguales)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape // Usa CircleShape para la sombra
                    )
                    .clip(CircleShape) // Usa CircleShape para recortar el contenido en forma circular
                    .background(Color(0xFF2A3D66)) // Puedes usar Color(0xFF2A3D66) o DeepBlue si lo definiste
                    .padding(35.dp) // Padding dentro del círculo antes de la imagen
            ) {
                Image(
                    painter = painterResource(id = R.drawable.scholrlogo),
                    contentDescription = "Logo Scholr",
                    modifier = Modifier.fillMaxSize(), // La imagen llenará el espacio dentro del círculo (menos el padding)
                    contentScale = ContentScale.Fit // Ajusta la imagen para que quepa sin distorsión
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Selecciona tu rol",
                fontSize = 28.sp,
                fontFamily = cabinFamily,
                fontWeight = FontWeight.Bold,
                color = DarkBrown
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Aventurero
            RoleButton(
                text = "Aventurero",
                onClick = {
                    viewModel.setRole("ROLE_ADVENTUROUS")
                    navController.navigate(NavScreenAdventurer.signup_screen.name)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "O",
                fontSize = 24.sp,
                fontFamily = cabinFamily,
                fontWeight = FontWeight.Medium,
                color = DarkBrown
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Empresario
            RoleButton(
                text = "Empresario",
                onClick = {
                    viewModel.setRole("ROLE_ENTREPRENEUR")
                    navController.navigate(NavScreenAdventurer.signup_screen.name)
                }
            )
        }
    }
}

@Composable
private fun RoleButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 32.dp)
            .shadow(4.dp, RoundedCornerShape(28.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryBrown,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(28.dp)
    ) {
        Text(
            text = text,
            fontSize = 22.sp,
            fontFamily = cabinFamily,
            fontWeight = FontWeight.Bold
        )
    }
}