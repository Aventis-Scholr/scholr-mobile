package com.example.aventurape_androidmobile.domains.applications.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.InputStream
import java.util.Date
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import com.example.aventurape_androidmobile.domains.management.screens.viewModels.HomeScholarshipsViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun EditPostulanteFormScreen(viewModel: HomeApplicationsViewModel,scholarshipsViewModel: HomeScholarshipsViewModel, navController: NavHostController, context: Context)
{
    LaunchedEffect(Unit) {
        scholarshipsViewModel.loadScholarshipsByCompany("BACKUS")
    }
    //para el drawer
    val drawerState= rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope= rememberCoroutineScope()

    // Datos del postulante
    var nameInput by remember { mutableStateOf(viewModel.applicationToEdit.postulante.nombres) }
    var lastNamesInput by remember { mutableStateOf(viewModel.applicationToEdit.postulante.apellidos) }
    var dniInput by remember { mutableStateOf(viewModel.applicationToEdit.postulante.dni.toString()) }
    var birthdayInput by remember { mutableStateOf(viewModel.applicationToEdit.postulante.fechaNacimiento) }

    // Contacto del postulante
    var emailInput by remember { mutableStateOf(viewModel.applicationToEdit.postulante.contacto.correo) }
    var phoneInput by remember { mutableStateOf(viewModel.applicationToEdit.postulante.contacto.celular.toString()) }

    // Centro de estudios
    var schoolNameInput by remember { mutableStateOf(viewModel.applicationToEdit.postulante.centroEstudios.nombre) }
    var schoolTypeInput by remember { mutableStateOf(viewModel.applicationToEdit.postulante.centroEstudios.tipo) }
    var schoolLevelInput by remember { mutableStateOf(viewModel.applicationToEdit.postulante.centroEstudios.nivel) }
    var schoolDepartmentInput by remember { mutableStateOf(viewModel.applicationToEdit.postulante.centroEstudios.departamento) }
    var schoolProvinceInput by remember { mutableStateOf(viewModel.applicationToEdit.postulante.centroEstudios.provincia) }
    var schoolDistrictInput by remember { mutableStateOf(viewModel.applicationToEdit.postulante.centroEstudios.distrito) }

    // Tipo de beca
    //var scholarshipTypeInput by remember { mutableStateOf(viewModel.applicationToEdit.scholarshipName) }
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

    fun uriToMultipartBodyPart(context: Context, uri: Uri?, partName: String): MultipartBody.Part? {
        if (uri == null) return null
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: return null
        val requestBody = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), bytes)
        return MultipartBody.Part.createFormData(partName, "file", requestBody)
    }

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
            Text("Editar Postulante",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(vertical = 25.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )

            // Sección de tipo de beca
            var expanded by remember { mutableStateOf(false) }
            val scholarships = scholarshipsViewModel.state.scholarships

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .padding(horizontal = 60.dp)
                    .padding(vertical = 10.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .background(Color.White),
                    readOnly = true,
                    value = scholarshipTypeInput,
                    onValueChange = {},
                    label = { Text("Seleccione una beca") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    scholarships.forEach { scholarship ->
                        DropdownMenuItem(
                            text = { Text(scholarship.name) },
                            onClick = {
                                scholarshipTypeInput = scholarship.name
                                expanded = false
                            }
                        )
                    }
                }
            }

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

            Text("Adjuntar documentos",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Blue
            )

            Text("DNI del postulante",
                modifier = Modifier
                    .padding(horizontal = 45.dp)
                    .padding(top = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            //seleccionar archivos
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { launcher1.launch("*/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4C542))
                ) {
                    Text("Adjuntar", color = Color.Black)
                }
                Spacer(modifier = Modifier.weight(1f))
                if (postulante_dni != null) {
                    Text(
                        "Archivo: ${postulante_dni?.lastPathSegment}",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            Text("Libreta de notas del postulante",
                modifier = Modifier
                    .padding(horizontal = 45.dp)
                    .padding(top = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { launcher2.launch("*/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4C542))
                ) {
                    Text("Adjuntar", color = Color.Black)
                }
                Spacer(modifier = Modifier.weight(1f))
                if (postulante_libreta_notas != null) {
                    Text(
                        "Archivo: ${postulante_libreta_notas?.lastPathSegment}",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            Text("Constancia de logro de aprendizaje del postulante",
                modifier = Modifier
                    .padding(horizontal = 45.dp)
                    .padding(top = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { launcher3.launch("*/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4C542))
                ) {
                    Text("Adjuntar", color = Color.Black)
                }
                Spacer(modifier = Modifier.weight(1f))
                if (postulante_const_logro_aprendizaje != null) {
                    Text(
                        "Archivo: ${postulante_const_logro_aprendizaje?.lastPathSegment}",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            Text("DNI del apoderado",
                modifier = Modifier
                    .padding(horizontal = 45.dp)
                    .padding(top = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { launcher4.launch("*/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4C542))
                ) {
                    Text("Adjuntar", color = Color.Black)
                }
                Spacer(modifier = Modifier.weight(1f))
                if (apoderado_dni != null) {
                    Text(
                        "Archivo: ${apoderado_dni?.lastPathSegment}",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            Text("Declaración jurada",
                modifier = Modifier
                    .padding(horizontal = 45.dp)
                    .padding(top = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { launcher5.launch("*/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4C542))
                ) {
                    Text("Adjuntar", color = Color.Black)
                }
                Spacer(modifier = Modifier.weight(1f))
                if (apoderado_declaracion_jurada != null) {
                    Text(
                        "Archivo: ${apoderado_declaracion_jurada?.lastPathSegment}",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

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
                            //tipoBeca = scholarshipTypeInput,
                            scholarshipName = scholarshipTypeInput,
                            postulante = postulante
                        )

                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                            // Llamar al ViewModel para crear la aplicación
                            println(application.postulante.nombres);
                            viewModel.updateApplication(application, viewModel.applicationToEdit.id.toLong())

                            val partDni = uriToMultipartBodyPart(context, postulante_dni, "postulante_dni")
                            val partLibreta = uriToMultipartBodyPart(context, postulante_libreta_notas, "postulante_libreta_notas")
                            val partConstancia = uriToMultipartBodyPart(context, postulante_const_logro_aprendizaje, "postulante_const_logro_aprendizaje")
                            val partApoderadoDni = uriToMultipartBodyPart(context, apoderado_dni, "apoderado_dni")
                            val partDeclaracion = uriToMultipartBodyPart(context, apoderado_declaracion_jurada, "apoderado_declaracion_jurada")


                            if (partDni != null && partLibreta != null && partConstancia != null && partApoderadoDni != null && partDeclaracion != null) {
                                viewModel.viewModelScope.launch(Dispatchers.IO) {
                                    viewModel.uploadApplicationFiles(
                                        viewModel.applicationToEdit.id.toLong(),
                                        partDni,
                                        partLibreta,
                                        partConstancia,
                                        partApoderadoDni,
                                        partDeclaracion
                                    ) { success, errorMessage ->
                                        if (success) {
                                            Log.d("AddPostulanteFormScreen", "Archivos subidos correctamente")
                                        } else {
                                            Log.e("AddPostulanteFormScreen", "Error al subir archivos: $errorMessage")
                                            // Aquí puedes mostrar el error al usuario
                                        }
                                    }
                                }
                            } else {
                                Log.e("AddPostulanteFormScreen", "Faltan archivos por adjuntar")
                                // Aquí puedes mostrar un mensaje al usuario si lo deseas
                            }
                        }

                        // Navegar de regreso
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
                            //tipoBeca = scholarshipTypeInput,
                            scholarshipName = scholarshipTypeInput,
                            postulante = postulante
                        )

                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                            // Llamar al ViewModel para crear la aplicación
                            viewModel.updateApplication(application, viewModel.applicationToEdit.id.toLong())

                            val partDni = uriToMultipartBodyPart(context, postulante_dni, "postulante_dni")
                            val partLibreta = uriToMultipartBodyPart(context, postulante_libreta_notas, "postulante_libreta_notas")
                            val partConstancia = uriToMultipartBodyPart(context, postulante_const_logro_aprendizaje, "postulante_const_logro_aprendizaje")
                            val partApoderadoDni = uriToMultipartBodyPart(context, apoderado_dni, "apoderado_dni")
                            val partDeclaracion = uriToMultipartBodyPart(context, apoderado_declaracion_jurada, "apoderado_declaracion_jurada")


                            if (partDni != null && partLibreta != null && partConstancia != null && partApoderadoDni != null && partDeclaracion != null) {
                                viewModel.viewModelScope.launch(Dispatchers.IO) {
                                    viewModel.uploadApplicationFiles(
                                        viewModel.applicationToEdit.id.toLong(),
                                        partDni,
                                        partLibreta,
                                        partConstancia,
                                        partApoderadoDni,
                                        partDeclaracion
                                    ) { success, errorMessage ->
                                        if (success) {
                                            Log.d("AddPostulanteFormScreen", "Archivos subidos correctamente")
                                        } else {
                                            Log.e("AddPostulanteFormScreen", "Error al subir archivos: $errorMessage")
                                            // Aquí puedes mostrar el error al usuario
                                        }
                                    }
                                }
                            } else {
                                Log.e("AddPostulanteFormScreen", "Faltan archivos por adjuntar")
                                // Aquí puedes mostrar un mensaje al usuario si lo deseas
                            }
                        }



                        // Navegar de regreso
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