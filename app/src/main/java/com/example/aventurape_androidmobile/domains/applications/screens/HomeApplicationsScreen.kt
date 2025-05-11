package com.example.aventurape_androidmobile.domains.applications.screens

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aventurape_androidmobile.domains.adventurer.screens.AdventureCard
import com.example.aventurape_androidmobile.domains.applications.models.Application
import com.example.aventurape_androidmobile.domains.applications.viewModels.HomeApplicationsViewModel
import com.example.aventurape_androidmobile.shared.components.TopBar

@Composable
fun HomeApplicationsScreen(viewModel: HomeApplicationsViewModel, navController: NavController) {

    LaunchedEffect(Unit) {
        viewModel.loadApplications()
    }

    Scaffold (modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar()
        }){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Text(
                text = "Tus Postulaciones",
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
            )

            // Lista de aplicaciones
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(viewModel.state.applications) { application ->
                    ApplicationCard(application, navController)
                }
            }

            //boton +
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate("select_postulacion_screen") },
                    modifier = Modifier
                        .align(Alignment.BottomCenter) // Alineación aplicada al contenedor
                        .padding(16.dp)
                        .size(72.dp) // Tamaño grande
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Add,
                        contentDescription = "Agregar",
                        modifier = Modifier.size(36.dp) // Tamaño del ícono
                    )
                }
            }

        }
    }
}

@Composable
fun ApplicationCard(application: Application, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(androidx.compose.ui.graphics.Color(0xFFF4C542))
    ) {
        Column (modifier = Modifier.padding(16.dp)) {
            Text(
                text = application.postulante.nombres + " " + application.postulante.apellidos,
                modifier = Modifier.padding(bottom = 10.dp),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Text(
                text = "Estado: " + application.status,
                modifier = Modifier.padding(bottom = 10.dp),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )

        }
    }
}
