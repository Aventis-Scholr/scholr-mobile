package com.example.aventurape_androidmobile.domains.applications.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aventurape_androidmobile.domains.applications.viewModels.HomeApplicationsViewModel

@Composable
fun DataApoderadoFormScreen(viewModel: HomeApplicationsViewModel, navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    // Cargar datos existentes si están disponibles
    /*LaunchedEffect(Unit) {
        val existingData = viewModel.loadDataApoderadoByApoderadoId(2)
        if (existingData != null) {
            nombre = existingData.nombres
            apellido = existingData.apellidos
            email = existingData.email
            telefono = existingData.telefono
        }
        isLoading = false
    }*/

    if (isLoading) {
        //Text(text = "Cargando datos...")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Formulario de Datos del Apoderado")

            // Campo de texto para el nombre
            BasicTextField(
                value = nombre,
                onValueChange = { nombre = it },
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        if (nombre.isEmpty()) Text("Nombre")
                        innerTextField()
                    }
                }
            )

            // Campo de texto para el apellido
            BasicTextField(
                value = apellido,
                onValueChange = { apellido = it },
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        if (apellido.isEmpty()) Text("Apellido")
                        innerTextField()
                    }
                }
            )

            // Campo de texto para el email
            BasicTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        if (email.isEmpty()) Text("Email")
                        innerTextField()
                    }
                }
            )

            // Campo de texto para el teléfono
            BasicTextField(
                value = telefono,
                onValueChange = { telefono = it },
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        if (telefono.isEmpty()) Text("Teléfono")
                        innerTextField()
                    }
                }
            )

            // Botón para guardar los datos
            Button(
                onClick = {
                    /*
                    viewModel.saveDataApoderado(
                        nombre = nombre,
                        apellido = apellido,
                        email = email,
                        telefono = telefono,
                    )*/
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Guardar")
            }
        }
    }
}