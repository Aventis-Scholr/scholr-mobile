package com.example.aventurape_androidmobile.domains.authentication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen (navController: NavHostController){
    val backgroundColor = Color(0xFFDCF1F9)
    // Navegar a la pantalla de inicio de sesión después de 3 segundos
    LaunchedEffect(Unit) {
        delay(3000) // Esperar 3 segundos
        navController.navigate(NavScreenAdventurer.login_screen.name) {
            popUpTo(NavScreenAdventurer.welcome_screen.name) {
                inclusive = true
            }
        }
    }

    Column(
        modifier= Modifier
            .fillMaxSize()
            .background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Box(
            modifier = Modifier
                .size(270.dp) // Define el tamaño del círculo (ancho y alto deben ser iguales)
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
        Text(text = "Scholr", fontFamily = cabinFamily, fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}