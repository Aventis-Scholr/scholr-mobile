package com.example.aventurape_androidmobile.domains.applications.screens

import android.R.attr.left
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.isPopupLayout
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.domains.adventurer.screens.AdventureCard
import com.example.aventurape_androidmobile.domains.applications.models.Application
import com.example.aventurape_androidmobile.domains.applications.viewModels.HomeApplicationsViewModel
import com.example.aventurape_androidmobile.shared.components.Drawer
import com.example.aventurape_androidmobile.shared.components.TopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeApplicationsScreen(viewModel: HomeApplicationsViewModel, navController: NavHostController, context: Context) {
    //para el drawer
    val drawerState= rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope= rememberCoroutineScope()


    // Obtener el ID del usuario logeado
    val userId = PreferenceManager.getUserId(context)

    LaunchedEffect(Unit) {
        viewModel.getApplicationsByApoderadoId(userId)
    }

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
                    ApplicationCard(viewModel, application, navController, userId)
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
                        .clip(RoundedCornerShape(100.dp)),
                    containerColor = Color(0xFF2A3D66)

                ) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Add,
                        contentDescription = "Agregar",
                        modifier = Modifier.size(36.dp), // Tamaño del ícono
                        tint = androidx.compose.ui.graphics.Color.White
                    )
                }
            }

        }
    }
}}

@Composable
fun ApplicationCard(viewModel: HomeApplicationsViewModel, application: Application, navController: NavController, userId: Long) {
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
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                if (application.status == "SINENVIAR")
                {
                    IconButton(
                        onClick = {
                            viewModel.viewModelScope.launch(Dispatchers.IO) {
                                viewModel.applicationToEdit = application;
                            }
                            navController.navigate("edit_postulante_form_screen")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = ""
                        ) }
                }

                IconButton(
                    onClick = {
                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                            viewModel.deleteApplication(application.id.toLong(), userId)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = ""
                    ) }
            }


        }
    }
}
