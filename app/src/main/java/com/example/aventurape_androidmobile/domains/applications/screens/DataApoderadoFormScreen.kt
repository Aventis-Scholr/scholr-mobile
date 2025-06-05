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
fun DataApoderadoFormScreen(viewModel: HomeApplicationsViewModel, navController: NavController, apoderadoId: Long) {
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var tipoDocumento by remember { mutableStateOf("") }
    var numeroDocumento by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    // Cargar datos existentes si están disponibles
    LaunchedEffect(apoderadoId) {
        viewModel.loadDataApoderadoByApoderadoId(apoderadoId)
        viewModel.state.dataApoderado?.let {
            nombres = it.nombres
            apellidos = it.apellidos
            fechaNacimiento = it.fechaNacimiento
            tipoDocumento = it.tipoDocumento
            numeroDocumento = it.numeroDocumento
            correo = it.correo
            celular = it.celular
        }
        isLoading = false
    }

    if (isLoading) {
        Text(text = "Cargando datos...")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Formulario de Datos del Apoderado")

            BasicTextField(value = nombres, onValueChange = { nombres = it }, modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.fillMaxWidth().padding(8.dp)) {
                    if (nombres.isEmpty()) Text("Nombres")
                    it()
                }
            }

            BasicTextField(value = apellidos, onValueChange = { apellidos = it }, modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.fillMaxWidth().padding(8.dp)) {
                    if (apellidos.isEmpty()) Text("Apellidos")
                    it()
                }
            }

            BasicTextField(value = fechaNacimiento, onValueChange = { fechaNacimiento = it }, modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.fillMaxWidth().padding(8.dp)) {
                    if (fechaNacimiento.isEmpty()) Text("Fecha de Nacimiento")
                    it()
                }
            }

            BasicTextField(value = tipoDocumento, onValueChange = { tipoDocumento = it }, modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.fillMaxWidth().padding(8.dp)) {
                    if (tipoDocumento.isEmpty()) Text("Tipo de Documento")
                    it()
                }
            }

            BasicTextField(value = numeroDocumento, onValueChange = { numeroDocumento = it }, modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.fillMaxWidth().padding(8.dp)) {
                    if (numeroDocumento.isEmpty()) Text("Número de Documento")
                    it()
                }
            }

            BasicTextField(value = correo, onValueChange = { correo = it }, modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.fillMaxWidth().padding(8.dp)) {
                    if (correo.isEmpty()) Text("Correo")
                    it()
                }
            }

            BasicTextField(value = celular, onValueChange = { celular = it }, modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.fillMaxWidth().padding(8.dp)) {
                    if (celular.isEmpty()) Text("Celular")
                    it()
                }
            }

            Button(
                onClick = {
                    viewModel.saveDataApoderado(
                        apoderadoId = apoderadoId,
                        nombres = nombres,
                        apellidos = apellidos,
                        fechaNacimiento = fechaNacimiento,
                        tipoDocumento = tipoDocumento,
                        numeroDocumento = numeroDocumento,
                        correo = correo,
                        celular = celular
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}