package com.example.aventurape_androidmobile.domains.applications.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aventurape_androidmobile.domains.applications.models.Contacto
import com.example.aventurape_androidmobile.domains.applications.models.CuentaBancaria
import com.example.aventurape_androidmobile.domains.applications.models.Domicilio
import com.example.aventurape_androidmobile.domains.applications.models.InformacionLaboral
import com.example.aventurape_androidmobile.domains.applications.viewModels.HomeApplicationsViewModel

@Composable
fun DataApoderadoFormScreen(viewModel: HomeApplicationsViewModel, navController: NavController, apoderadoId: Long) {
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Cargar datos existentes si están disponibles
    LaunchedEffect(apoderadoId) {
        viewModel.loadDataApoderadoByApoderadoId(apoderadoId)
        viewModel.state.dataApoderado?.let {
            // Asumiendo que ViewModel expone variables mutables de estado
            viewModel.nombres = it.nombres
            viewModel.apellidos = it.apellidos
            viewModel.fechaNacimiento = it.fechaNacimiento
            viewModel.tipoDocumento = it.tipoDocumento
            viewModel.numeroDocumento = it.dni

            viewModel.correo = it.contacto.correo
            viewModel.celular = it.contacto.celular

            viewModel.entidadBancaria = it.cuentaBancaria.entidadBancaria
            viewModel.numeroCuenta = it.cuentaBancaria.numeroCuenta
            viewModel.cci = it.cuentaBancaria.cci

            viewModel.direccion = it.domicilio.direccion
            viewModel.departamento = it.domicilio.departamento
            viewModel.provincia = it.domicilio.provincia
            viewModel.distrito = it.domicilio.distrito

            viewModel.tipoColaborador = it.informacionLaboral.tipoColaborador
            viewModel.cargo = it.informacionLaboral.cargo
            viewModel.sede = it.informacionLaboral.sede
            viewModel.local = it.informacionLaboral.local
            viewModel.ingreso = it.informacionLaboral.ingreso.toString()
        }
        isLoading = false
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Cargando datos...", fontSize = 18.sp)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFDCEFF8))
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text("Tus datos", fontSize = 20.sp, color = Color(0xFF001F3F))

            OutlinedTextField(
                value = viewModel.nombres,
                onValueChange = { viewModel.nombres = it },
                label = { Text("Nombres") },
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                value = viewModel.apellidos,
                onValueChange = { viewModel.apellidos = it },
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                value = viewModel.fechaNacimiento,
                onValueChange = { viewModel.fechaNacimiento = it },
                label = { Text("F. de Nacimiento") },
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Documento de Identidad", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)  // Ancho total máximo 80%
                        .align(Alignment.Center), // Centra la fila dentro del Box
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio fijo entre campos
                ) {
                    OutlinedTextField(
                        value = viewModel.tipoDocumento,
                        onValueChange = { viewModel.tipoDocumento = it },
                        label = { Text("Tipo") },
                        modifier = Modifier.weight(1f)  // Cada campo ocupa mitad del 80%
                    )
                    OutlinedTextField(
                        value = viewModel.numeroDocumento,
                        onValueChange = { viewModel.numeroDocumento = it },
                        label = { Text("Numero Doc.") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Contacto", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = viewModel.correo,
                onValueChange = { viewModel.correo = it },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = viewModel.celular,
                onValueChange = { viewModel.celular = it },
                label = { Text("Celular") },
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Abono", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = viewModel.entidadBancaria,
                onValueChange = { viewModel.entidadBancaria = it },
                label = { Text("Entidad bancaria") },
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = viewModel.numeroCuenta,
                onValueChange = { viewModel.numeroCuenta = it },
                label = { Text("Nro de Cta") },
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = viewModel.cci,
                onValueChange = { viewModel.cci = it },
                label = { Text("CCI") },
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Domicilio", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = viewModel.direccion,
                onValueChange = { viewModel.direccion = it },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.Center), // Centra el Row dentro del Box
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio fijo entre campos
                ) {
                    OutlinedTextField(
                        value = viewModel.departamento,
                        onValueChange = { viewModel.departamento = it },
                        label = { Text("Dpto.") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = viewModel.provincia,
                        onValueChange = { viewModel.provincia = it },
                        label = { Text("Provincia") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            OutlinedTextField(
                value = viewModel.distrito,
                onValueChange = { viewModel.distrito = it },
                label = { Text("Distrito") },
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Información Laboral", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = viewModel.tipoColaborador,
                onValueChange = { viewModel.tipoColaborador = it },
                label = { Text("Tipo de colaborador") },
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = viewModel.cargo,
                onValueChange = { viewModel.cargo = it },
                label = { Text("Cargo") },
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.sede,
                        onValueChange = { viewModel.sede = it },
                        label = { Text("Sede") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = viewModel.local,
                        onValueChange = { viewModel.local = it },
                        label = { Text("Local") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            OutlinedTextField(
                value = viewModel.ingreso,
                onValueChange = { viewModel.ingreso = it },
                label = { Text("Ingreso") },
                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Adjuntar", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Button(
                onClick = { /* lógica de adjuntar (pendiente) */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9A50D)),
                modifier = Modifier
                    .fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
                    .height(50.dp)
            ) {
                Text("Seleccionar archivos")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.acepto,
                    onCheckedChange = { viewModel.acepto = it }
                )
                Text("Acepto los términos y condiciones")
            }

            Button(
                onClick = {
                    val ingresoInt = viewModel.ingreso.toIntOrNull()

                    if (viewModel.acepto) {
                        if (ingresoInt != null) {
                            viewModel.saveDataApoderado(
                                apoderadoId = 1L,
                                nombres = viewModel.nombres,
                                apellidos = viewModel.apellidos,
                                fechaNacimiento = viewModel.fechaNacimiento,
                                dni = viewModel.numeroDocumento,
                                tipoDocumento = viewModel.tipoDocumento,
                                contacto = Contacto(viewModel.correo, viewModel.celular),
                                domicilio = Domicilio(viewModel.direccion, viewModel.departamento, viewModel.provincia, viewModel.distrito),
                                cuentaBancaria = CuentaBancaria(viewModel.entidadBancaria, viewModel.numeroCuenta, viewModel.cci),
                                informacionLaboral = InformacionLaboral(
                                    viewModel.tipoColaborador,
                                    viewModel.cargo,
                                    viewModel.sede,
                                    viewModel.local,
                                    ingresoInt
                                )
                            )
                        } else {
                            Toast.makeText(context, "Por favor ingresa un número válido en el campo ingreso", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Guardar y cerrar")
            }
        }
    }
}