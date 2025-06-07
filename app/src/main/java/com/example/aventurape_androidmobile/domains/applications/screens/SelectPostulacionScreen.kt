package com.example.aventurape_androidmobile.domains.applications.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.domains.applications.viewModels.HomeApplicationsViewModel
import com.example.aventurape_androidmobile.shared.components.Drawer
import com.example.aventurape_androidmobile.shared.components.TopBar
import kotlinx.coroutines.launch

@Composable
fun SelectPostulacionScreen(viewModel: HomeApplicationsViewModel, navController: NavHostController) {
    //para el drawer
    val drawerState= rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope= rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState=drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Drawer(navController)
            }
        }
    ) {

    Scaffold (modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(onOpenDrawer = {
                scope.launch {
                    drawerState.apply {
                        if(isClosed)
                            open()
                        else
                            close()
                    }
                }
            })
        }){ paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Botón Datos colaborador
                FloatingActionButton(
                    onClick = { navController.navigate("data_apoderado_form_screen") },
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .width(250.dp),
                    containerColor = Color(0xFFF4C542)
                ) {
                    Text(
                        text = "Datos del Colaborador",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.padding(start = 160.dp)
                    )
                }

                // Botón añadir postulante
                FloatingActionButton(
                    onClick = { navController.navigate("add_postulante_form_screen") },
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .width(250.dp),
                    containerColor = Color(0xFFF4C542)
                ) {
                    Text(
                        text = "Añadir Postulante",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.padding(start = 160.dp)
                    )
                }
            }

            // Botón volver a bandeja
            FloatingActionButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Alineación aplicada al contenedor
                    .padding(16.dp),
                containerColor = Color(0xFF2A3D66)
            ) {
                Text(
                    text = "Volver a Bandeja",
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}}
