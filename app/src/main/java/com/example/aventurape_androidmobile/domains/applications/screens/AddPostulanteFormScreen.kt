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
import com.example.aventurape_androidmobile.domains.applications.screens.components.uriToMultipartPart
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

    // Obtener el ID del usuario logeado
    val userId = PreferenceManager.getUserId(context)

    // Datos del postulante
    val nameInput by viewModel.nameInput
    val lastNamesInput by viewModel.lastNamesInput
    val dniInput by viewModel.dniInput
    val birthdayInput by viewModel.birthdayInput

    val emailInput by viewModel.emailInput
    val phoneInput by viewModel.phoneInput

    val schoolNameInput by viewModel.schoolNameInput
    val schoolTypeInput by viewModel.schoolTypeInput
    val schoolLevelInput by viewModel.schoolLevelInput
    val schoolDepartmentInput by viewModel.schoolDepartmentInput
    val schoolProvinceInput by viewModel.schoolProvinceInput
    val schoolDistrictInput by viewModel.schoolDistrictInput

    val scholarshipTypeInput by viewModel.scholarshipTypeInput

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
                onValueChange = { nuevoValor ->
                    viewModel.scholarshipTypeInput.value = nuevoValor },
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
                onValueChange = { nuevoValor ->
                    viewModel.nameInput.value = nuevoValor  },
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
                onValueChange = { nuevoValor ->
                    viewModel.lastNamesInput.value = nuevoValor},
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
                onValueChange = { nuevoValor ->
                    viewModel.dniInput.value = nuevoValor },
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
                onValueChange = { nuevoValor ->
                    viewModel.birthdayInput.value = nuevoValor },
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
                onValueChange = { nuevoValor ->
                    viewModel.emailInput.value = nuevoValor },
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
                onValueChange = { nuevoValor ->
                    viewModel.phoneInput.value = nuevoValor },
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
                onValueChange = { nuevoValor ->
                    viewModel.schoolNameInput.value = nuevoValor },
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
                onValueChange = { nuevoValor ->
                    viewModel.schoolTypeInput.value = nuevoValor },
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
                onValueChange = { nuevoValor ->
                    viewModel.schoolLevelInput.value = nuevoValor },
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
                onValueChange = { nuevoValor ->
                    viewModel.schoolDepartmentInput.value = nuevoValor },
                placeholder = { Text("Departamento") }
            )

            // Provincia
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = schoolProvinceInput,
                onValueChange = { nuevoValor ->
                    viewModel.schoolProvinceInput.value = nuevoValor },
                placeholder = { Text("Provincia") }
            )

            // Distrito
            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = schoolDistrictInput,
                onValueChange = { nuevoValor ->
                    viewModel.schoolDistrictInput.value = nuevoValor },
                placeholder = { Text("Distrito") }
            )

            // Subir PDFs
            Button(
                onClick = {
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

                    val application = ApplicationRequest(
                        id = 0,
                        idApoderado = userId.toInt(),
                        status = "SINENVIAR",
                        scholarshipName = scholarshipTypeInput,
                        postulante = postulante
                    )

                    viewModel.createApplication(
                        application = application,
                        apoderadoId = userId.toLong(),
                        onSuccessNavigation = { applicationId ->
                            // ✅ Aquí rediriges al subir archivos
                            navController.navigate("uploadDocuments/$applicationId")
                        }
                    )
                },
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .padding(horizontal = 100.dp)
                    .fillMaxWidth()
            ) {
                Text("Subir Archivos PDF")
            }


            //--------------------------------

                // Botón para guardar
            Button(
                onClick = {
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

                    val application = ApplicationRequest(
                        id = 0,
                        idApoderado = userId.toInt(),
                        status = "SINENVIAR",
                        scholarshipName = scholarshipTypeInput,
                        postulante = postulante
                    )

                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        // Crear aplicación sin navegación, solo mostrar éxito o error
                        viewModel.createApplication(application, userId) { createdApplicationId ->
                            // Puedes usar createdApplicationId si quieres, o simplemente mostrar mensaje
                            // En este caso, NO navegamos, solo cerrar formulario o mostrar mensaje
                        }
                    }

                    // Aquí no haces navegación ni popBackStack porque debe ser controlado en el callback o por el usuario
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

                    val application = ApplicationRequest(
                        id = 0,
                        idApoderado = userId.toInt(),
                        status = "PENDIENTE",
                        scholarshipName = scholarshipTypeInput,
                        postulante = postulante
                    )

                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        viewModel.createApplication(application, userId) { createdApplicationId ->
                            // Navegar a pantalla de subir documentos tras crear aplicación
                            navController.navigate("uploadDocuments/$createdApplicationId")
                        }
                    }

                    // No hacer popBackStack aquí porque la navegación está dentro del callback
                },
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .padding(horizontal = 100.dp)
                    .fillMaxWidth()
            ) {
                Text("Finalizar y Enviar")
            }
        }
    }
}}