package com.example.aventurape_androidmobile.domains.applications.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.domains.applications.models.Application
import com.example.aventurape_androidmobile.domains.applications.models.ApplicationRequest
import com.example.aventurape_androidmobile.domains.applications.viewModels.HomeApplicationsViewModel
import com.example.aventurape_androidmobile.shared.components.Drawer
import com.example.aventurape_androidmobile.shared.components.TopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

@Composable
fun AddPostulanteFormScreen(viewModel: HomeApplicationsViewModel, navController: NavHostController, context: Context)
{
    //para el drawer
    val drawerState= rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope= rememberCoroutineScope()

    // Datos del postulante
    var nameInput by remember { mutableStateOf("") }
    var lastNamesInput by remember { mutableStateOf("") }
    var dniInput by remember { mutableStateOf("") }
    var birthdayInput by remember { mutableStateOf("") }

    // Contacto del postulante
    var emailInput by remember { mutableStateOf("") }
    var phoneInput by remember { mutableStateOf("") }

    // Centro de estudios
    var schoolNameInput by remember { mutableStateOf("") }
    var schoolTypeInput by remember { mutableStateOf("") }
    var schoolLevelInput by remember { mutableStateOf("") }
    var schoolDepartmentInput by remember { mutableStateOf("") }
    var schoolProvinceInput by remember { mutableStateOf("") }
    var schoolDistrictInput by remember { mutableStateOf("") }

    // Tipo de beca
    var scholarshipTypeInput by remember { mutableStateOf("") }

    // Obtener el ID del usuario logeado
    val userId = PreferenceManager.getUserId(context)

    //parte para archivo------------------
    var postulante_dni by remember { mutableStateOf<Uri?>(null) }
    val launcher1 = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        postulante_dni = uri
    }

    var postulante_libreta_notas by remember { mutableStateOf<Uri?>(null) }
    val launcher2 = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        postulante_libreta_notas = uri
    }

    var postulante_const_logro_aprendizaje by remember { mutableStateOf<Uri?>(null) }
    val launcher3 = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        postulante_const_logro_aprendizaje = uri
    }

    var apoderado_dni by remember { mutableStateOf<Uri?>(null) }
    val launcher4 = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        apoderado_dni = uri
    }

    var apoderado_declaracion_jurada by remember { mutableStateOf<Uri?>(null) }
    val launcher5 = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        apoderado_declaracion_jurada = uri
    }

    //----------------------------------

    ModalNavigationDrawer(
        drawerState=drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Drawer(navController)
            }
        }
    ) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
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
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(220,241,249))
                .verticalScroll(rememberScrollState())  // Para hacer scroll si el contenido es largo
        ) {
            Text("Añadir Postulante",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(vertical = 25.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )

            // Sección de tipo de beca
            Text("Tipo de Beca",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Blue
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = scholarshipTypeInput,
                onValueChange = { scholarshipTypeInput = it },
                placeholder = { Text("MERITO, DEPORTIVA, ECONOMICA, CULTURAL") }
            )

            // Sección de información personal
            Text("Información Personal",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Blue
            )

            // Campo de Nombres
            Text("Nombres",
                modifier = Modifier
                    .padding(horizontal = 45.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = nameInput,
                onValueChange = { nameInput = it },
                placeholder = { Text("Ingrese sus Nombres") }
            )

            // Campo de Apellidos
            Text("Apellidos",
                modifier = Modifier
                    .padding(horizontal = 45.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = lastNamesInput,
                onValueChange = { lastNamesInput = it },
                placeholder = { Text("Ingrese sus Apellidos") }
            )

            // Campo de DNI
            Text("DNI",
                modifier = Modifier
                    .padding(horizontal = 45.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = dniInput,
                onValueChange = { dniInput = it },
                placeholder = { Text("Ingrese su DNI") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Campo de Fecha de Nacimiento
            Text("Fecha de Nacimiento",
                modifier = Modifier
                    .padding(horizontal = 45.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = birthdayInput,
                onValueChange = { birthdayInput = it },
                placeholder = { Text("YYYY-MM-DD") }
            )

            // Sección de contacto
            Text("Contacto del Postulante",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Blue
            )

            // Campo de Email
            Text("Correo electrónico",
                modifier = Modifier
                    .padding(horizontal = 45.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = emailInput,
                onValueChange = { emailInput = it },
                placeholder = { Text("Ingrese su correo electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Campo de Teléfono
            Text("Celular",
                modifier = Modifier
                    .padding(horizontal = 45.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = phoneInput,
                onValueChange = { phoneInput = it },
                placeholder = { Text("Ingrese su número de celular") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            // Sección de centro de estudios
            Text("Centro de Estudios",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Blue
            )

            // Campo de Nombre del centro
            Text("Nombre del Centro de Estudios",
                modifier = Modifier
                    .padding(horizontal = 45.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = schoolNameInput,
                onValueChange = { schoolNameInput = it },
                placeholder = { Text("Ingrese el nombre del centro de estudios") }
            )

            // Campo de Tipo de centro
            Text("Tipo de Centro",
                modifier = Modifier
                    .padding(horizontal = 45.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = schoolTypeInput,
                onValueChange = { schoolTypeInput = it },
                placeholder = { Text("Ej: Público, Privado") }
            )

            // Campo de Nivel educativo
            Text("Nivel Educativo",
                modifier = Modifier
                    .padding(horizontal = 45.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = schoolLevelInput,
                onValueChange = { schoolLevelInput = it },
                placeholder = { Text("Ej: Inicial, Primaria, Secundaria") }
            )

            // Campos de ubicación del centro
            Text("Ubicación del Centro",
                modifier = Modifier
                    .padding(horizontal = 45.dp)
                    .padding(top = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            // Departamento
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = schoolDepartmentInput,
                onValueChange = { schoolDepartmentInput = it },
                placeholder = { Text("Departamento") }
            )

            // Provincia
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = schoolProvinceInput,
                onValueChange = { schoolProvinceInput = it },
                placeholder = { Text("Provincia") }
            )

            // Distrito
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = schoolDistrictInput,
                onValueChange = { schoolDistrictInput = it },
                placeholder = { Text("Distrito") }
            )

            // Botón para seleccionar archivo
            Button(onClick = { launcher1.launch("*/*") }, modifier = Modifier
                .padding(vertical = 5.dp)
                .padding(horizontal = 100.dp)
                .fillMaxWidth()) {
                Text("dni del postulante")
            }
            if (postulante_dni != null) {
                Text("Archivo seleccionado: ${postulante_dni?.lastPathSegment}")
            }

            Button(onClick = { launcher2.launch("*/*") }, modifier = Modifier
                .padding(vertical = 5.dp)
                .padding(horizontal = 100.dp)
                .fillMaxWidth()) {
                Text("libre de notas del postulante")
            }
            if (postulante_libreta_notas != null) {
                Text("Archivo seleccionado: ${postulante_libreta_notas?.lastPathSegment}")
            }

            Button(onClick = { launcher3.launch("*/*") }, modifier = Modifier
                .padding(vertical = 5.dp)
                .padding(horizontal = 100.dp)
                .fillMaxWidth()) {
                Text("constancia de logro de aprendizaje del postulante")
            }
            if (postulante_const_logro_aprendizaje != null) {
                Text("Archivo seleccionado: ${postulante_const_logro_aprendizaje?.lastPathSegment}")
            }

            Button(onClick = { launcher4.launch("*/*") }, modifier = Modifier
                .padding(vertical = 5.dp)
                .padding(horizontal = 100.dp)
                .fillMaxWidth()) {
                Text("dni del apoderado")
            }
            if (apoderado_dni != null) {
                Text("Archivo seleccionado: ${apoderado_dni?.lastPathSegment}")
            }

            Button(onClick = { launcher5.launch("*/*") }, modifier = Modifier
                .padding(vertical = 5.dp)
                .padding(horizontal = 100.dp)
                .fillMaxWidth()) {
                Text("declaración jurada")
            }
            if (apoderado_declaracion_jurada != null) {
                Text("Archivo seleccionado: ${apoderado_declaracion_jurada?.lastPathSegment}")
            }

            //--------------------------------

                // Botón para guardar
                Button(
                    onClick = {
                        // Crear el objeto Postulante
                        val postulante = ApplicationRequest.Postulante(
                            nombres = nameInput,
                            apellidos = lastNamesInput,
                            dni = dniInput.toIntOrNull() ?: 0,
                            fechaNacimiento = birthdayInput,
                            contacto = ApplicationRequest.ContactoPostulante(
                                correo = emailInput,
                                celular = phoneInput.toIntOrNull() ?: 0
                            ),
                            centroEstudios = ApplicationRequest.CentroEstudios(
                                nombre = schoolNameInput,
                                tipo = schoolTypeInput,
                                nivel = schoolLevelInput,
                                departamento = schoolDepartmentInput,
                                provincia = schoolProvinceInput,
                                distrito = schoolDistrictInput
                            )
                        )

                        // Crear el objeto Application completo
                        val application = ApplicationRequest(
                            id = 0, // El backend probablemente asignará un ID
                            idApoderado = userId.toInt(), // Usar el ID del usuario logeado
                            status = "SINENVIAR", // Estado inicial
                            scholarshipName = scholarshipTypeInput,
                            postulante = postulante
                        )

                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                            // Llamar al ViewModel para crear la aplicación
                            viewModel.createApplication(application, userId)
                        }



                        // Navegar de regreso
                        navController.popBackStack()
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .padding(horizontal = 100.dp)
                        .fillMaxWidth()
                ) {
                    Text("Guardar Y Cerrar")
                }

                Button(
                    onClick = {
                        // Crear el objeto Postulante
                        val postulante = ApplicationRequest.Postulante(
                            nombres = nameInput,
                            apellidos = lastNamesInput,
                            dni = dniInput.toIntOrNull() ?: 0,
                            fechaNacimiento = birthdayInput,
                            contacto = ApplicationRequest.ContactoPostulante(
                                correo = emailInput,
                                celular = phoneInput.toIntOrNull() ?: 0
                            ),
                            centroEstudios = ApplicationRequest.CentroEstudios(
                                nombre = schoolNameInput,
                                tipo = schoolTypeInput,
                                nivel = schoolLevelInput,
                                departamento = schoolDepartmentInput,
                                provincia = schoolProvinceInput,
                                distrito = schoolDistrictInput
                            )
                        )

                        // Crear el objeto Application completo
                        val application = ApplicationRequest(
                            id = 0, // El backend probablemente asignará un ID
                            idApoderado = userId.toInt(), // Usar el ID del usuario logeado
                            status = "PENDIENTE", // Estado inicial
                            scholarshipName = scholarshipTypeInput,
                            postulante = postulante
                        )

                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                            // Llamar al ViewModel para crear la aplicación
                            viewModel.createApplication(application, userId)
                        }



                        // Navegar de regreso
                        navController.popBackStack()
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .padding(horizontal = 100.dp)
                        .fillMaxWidth()
                ) {
                    Text("Finalizar y Enviar")
                }

            if (viewModel.createApplicationSuccess) {
                Text(
                    "Aplicación creada con éxito",
                    color = Color.Green,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}}