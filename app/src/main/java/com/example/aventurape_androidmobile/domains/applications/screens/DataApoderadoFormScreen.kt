package com.example.aventurape_androidmobile.domains.applications.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.domains.applications.models.DataApoderado
import com.example.aventurape_androidmobile.domains.applications.viewModels.HomeApplicationsViewModel
import com.example.aventurape_androidmobile.shared.components.Drawer
import com.example.aventurape_androidmobile.shared.components.TopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DataApoderadoFormScreen(viewModel: HomeApplicationsViewModel, navController: NavHostController, context: Context) {
    //para el drawer
    val drawerState= rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope= rememberCoroutineScope()


    //var id
    var nombresInput by remember { mutableStateOf("") }
    var apellidosInput by remember { mutableStateOf("") }
    var dniInput by remember { mutableStateOf("") } //int
    var fechaNacimientoInput by remember { mutableStateOf("") }

    //contacto
    var correoInput by remember { mutableStateOf("") }
    var celularInput by remember { mutableStateOf("") } //int

    //domicilio
    var direccionInput by remember { mutableStateOf("") }
    var departamentoInput by remember { mutableStateOf("") }
    var provinciaInput by remember { mutableStateOf("") }
    var distritoInput by remember { mutableStateOf("") }

    //cuenta bancaria
    var entidadBancariaInput by remember { mutableStateOf("") }
    var numeroCuentaInput by remember { mutableStateOf("") } //int
    var cciInput by remember { mutableStateOf("") } //int

    //informacion laboral
    var tipoColaboradorInput by remember { mutableStateOf("") }
    var cargoInput by remember { mutableStateOf("") }
    var sedeInput by remember { mutableStateOf("") }
    var localInput by remember { mutableStateOf("") }
    var ingresoInput by remember { mutableStateOf("") } //int

    var isLoading by remember { mutableStateOf(true) }

    val userId = PreferenceManager.getUserId(context)

    var dataApoderadoExists by remember { mutableStateOf(false) }

    val dataApoderadoLoad = viewModel.state.dataApoderado
    //como es asincrono, si no se usa primero su m

    // Cargar datos existentes si están disponibles
    LaunchedEffect(userId) {
        viewModel.getDataApoderadoByApoderadoId(userId)
    }

    LaunchedEffect(dataApoderadoLoad) {
        if (dataApoderadoLoad != null) {
            nombresInput = dataApoderadoLoad.nombres
            apellidosInput = dataApoderadoLoad.apellidos
            dniInput = dataApoderadoLoad.dni.toString()
            fechaNacimientoInput = dataApoderadoLoad.fechaNacimiento.substring(0, 10)

            correoInput = dataApoderadoLoad.contacto.correo
            celularInput = dataApoderadoLoad.contacto.celular.toString()

            direccionInput = dataApoderadoLoad.domicilio.direccion
            departamentoInput = dataApoderadoLoad.domicilio.departamento
            provinciaInput = dataApoderadoLoad.domicilio.provincia
            distritoInput = dataApoderadoLoad.domicilio.distrito

            entidadBancariaInput = dataApoderadoLoad.cuentaBancaria.entidadBancaria
            numeroCuentaInput = dataApoderadoLoad.cuentaBancaria.numeroCuenta.toString()
            cciInput = dataApoderadoLoad.cuentaBancaria.cci.toString()

            tipoColaboradorInput = dataApoderadoLoad.informacionLaboral.tipoColaborador
            cargoInput = dataApoderadoLoad.informacionLaboral.cargo
            sedeInput = dataApoderadoLoad.informacionLaboral.sede
            localInput = dataApoderadoLoad.informacionLaboral.local
            ingresoInput = dataApoderadoLoad.informacionLaboral.ingreso.toString()

            dataApoderadoExists = true // Indicar que los datos ya existen

        } else {
            nombresInput = ""
            apellidosInput = ""
            dniInput = ""
            fechaNacimientoInput = ""

            correoInput = ""
            celularInput = ""

            direccionInput = ""
            departamentoInput = ""
            provinciaInput = ""
            distritoInput = ""

            entidadBancariaInput = ""
            numeroCuentaInput = ""
            cciInput = ""

            tipoColaboradorInput = ""
            cargoInput = ""
            sedeInput = ""
            localInput = ""
            ingresoInput = ""

            dataApoderadoExists = false
        }
        isLoading = false
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
    if (isLoading) {
        Text(text = "Cargando datos...")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(220,241,249))
                .verticalScroll(rememberScrollState())  // Para hacer scroll si el contenido es largo
        ) {
            Text(
                "Datos del Apoderado",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(vertical = 25.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = nombresInput,
                onValueChange = { nombresInput = it },
                label = { Text("Nombres") },
                placeholder = { Text("Ingrese sus nombres") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = apellidosInput,
                onValueChange = { apellidosInput = it },
                label = { Text("Apellidos") },
                placeholder = { Text("Ingrese sus apellidos") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = dniInput,
                onValueChange = { dniInput = it },
                label = { Text("DNI") },
                placeholder = { Text("Ingrese su numero de dni") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = fechaNacimientoInput,
                onValueChange = { fechaNacimientoInput = it },
                label = { Text("Fecha de nacimiento") },
                placeholder = { Text("Fecha de nacimiento (YYY-MM-DD)") }
            )

            // Sección de contacto
            Text(
                "Contacto",
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
                value = correoInput,
                onValueChange = { correoInput = it },
                label = { Text("Correo") },
                placeholder = { Text("Ingrese su correo") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = celularInput,
                onValueChange = { celularInput = it },
                label = { Text("Celular") },
                placeholder = { Text("Ingrese su n° de celular") }
            )

            // Sección de domicilio
            Text(
                "Domicilio",
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
                value = direccionInput,
                onValueChange = { direccionInput = it },
                label = { Text("Dirección") },
                placeholder = { Text("Ingrese su dirección") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = departamentoInput,
                onValueChange = { departamentoInput = it },
                label = { Text("Departamento") },
                placeholder = { Text("Ingrese su departamento") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = provinciaInput,
                onValueChange = { provinciaInput = it },
                label = { Text("Provincia") },
                placeholder = { Text("Ingrese su provincia") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = distritoInput,
                onValueChange = { distritoInput = it },
                label = { Text("Distrito") },
                placeholder = { Text("Ingrese su distrito") }
            )

            // Sección de cuenta bancaria
            Text(
                "Cuenta Bancaria",
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
                value = entidadBancariaInput,
                onValueChange = { entidadBancariaInput = it },
                label = { Text("Entidad Bancaria") },
                placeholder = { Text("Ingrese la entidad bancaria") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = numeroCuentaInput,
                onValueChange = { numeroCuentaInput = it },
                label = { Text("Número de Cuenta") },
                placeholder = { Text("Ingrese su numero de cuenta") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = cciInput,
                onValueChange = { cciInput = it },
                label = { Text("CCI") },
                placeholder = { Text("Ingrese el cci") }
            )

            // Sección de informacion laboral
            Text(
                "Información Laboral",
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
                value = tipoColaboradorInput,
                onValueChange = { tipoColaboradorInput = it },
                label = { Text("Tipo de Colaborador") },
                placeholder = { Text("Tipo de colaborador") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = cargoInput,
                onValueChange = { cargoInput = it },
                label = { Text("Cargo") },
                placeholder = { Text("Cargo") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = sedeInput,
                onValueChange = { sedeInput = it },
                label = { Text("Sede") },
                placeholder = { Text("Sede") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = localInput,
                onValueChange = { localInput = it },
                label = { Text("Local") },
                placeholder = { Text("Local") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 60.dp)
                    .background(Color.White),
                value = ingresoInput,
                onValueChange = { ingresoInput = it },
                label = { Text("Ingreso") },
                placeholder = { Text("Ingreso") }
            )

            Button(
                onClick = {
                    val contacto = DataApoderado.Contacto(
                        correo = correoInput,
                        celular = celularInput.toIntOrNull() ?: 0
                    )
                    val domicilio = DataApoderado.Domicilio(
                        direccion = direccionInput,
                        departamento = departamentoInput,
                        provincia = provinciaInput,
                        distrito = distritoInput
                    )
                    val cuentaBancaria = DataApoderado.CuentaBancaria(
                        entidadBancaria = entidadBancariaInput,
                        numeroCuenta = numeroCuentaInput.toIntOrNull() ?: 0,
                        cci = cciInput.toIntOrNull() ?: 0
                    )
                    val informacionLaboral = DataApoderado.InformacionLaboral(
                        tipoColaborador = tipoColaboradorInput,
                        cargo = cargoInput,
                        sede = sedeInput,
                        local = localInput,
                        ingreso = ingresoInput.toIntOrNull() ?: 0
                    )

                    //crear objeto DataApoderado
                    val dataApoderado = DataApoderado(
                        nombres = nombresInput,
                        apellidos = apellidosInput,
                        dni = dniInput.toIntOrNull() ?: 0,
                        fechaNacimiento = fechaNacimientoInput,
                        contacto = contacto,
                        domicilio = domicilio,
                        cuentaBancaria = cuentaBancaria,
                        informacionLaboral = informacionLaboral
                    )

                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        // Llamar al ViewModel para crear la aplicación
                        if (dataApoderadoExists == true) {
                            viewModel.updateDataApoderado(dataApoderado, userId)
                        } else
                        viewModel.createDataApoderado(dataApoderado, userId)
                    }

                    navController.popBackStack()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if(dataApoderadoExists) {
                    Text("Actualizar datos")
                } else
                Text("Guardar datos")
            }
        }
    }
}}}