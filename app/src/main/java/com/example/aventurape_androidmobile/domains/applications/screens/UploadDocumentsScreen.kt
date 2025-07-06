package com.example.aventurape_androidmobile.domains.applications.screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.domains.applications.screens.components.ArchivoUploadField
import com.example.aventurape_androidmobile.domains.applications.screens.components.uriToMultipartPart
import com.example.aventurape_androidmobile.domains.applications.viewModels.HomeApplicationsViewModel
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import com.example.aventurape_androidmobile.shared.components.TopBar

@Composable
fun UploadDocumentsScreen(
    applicationId: Long,
    viewModel: HomeApplicationsViewModel,
    navController: NavHostController,
    context: Context
) {

    val dniUri by viewModel.postulanteDniUri
    val libretaUri by viewModel.postulanteLibretaNotasUri
    val constanciaUri by viewModel.postulanteConstLogroAprendizajeUri
    val apoderadoDniUri by viewModel.apoderadoDniUri
    val declaracionUri by viewModel.apoderadoDeclaracionJuradaUri

    Scaffold(
        topBar = {
            TopBar(onOpenDrawer = {})  // Puedes personalizar si quieres drawer
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Text(
                "Subir Documentos PDF",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ArchivoUploadField("DNI del Postulante", dniUri) { uri, _ -> viewModel.postulanteDniUri.value = uri }
            ArchivoUploadField("Libreta de Notas", libretaUri) { uri, _ -> viewModel.postulanteLibretaNotasUri.value = uri }
            ArchivoUploadField("Constancia de Logro", constanciaUri) { uri, _ -> viewModel.postulanteConstLogroAprendizajeUri.value = uri }
            ArchivoUploadField("DNI del Apoderado", apoderadoDniUri) { uri, _ -> viewModel.apoderadoDniUri.value = uri }
            ArchivoUploadField("Declaración Jurada", declaracionUri) { uri, _ -> viewModel.apoderadoDeclaracionJuradaUri.value = uri }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.popBackStack() // Vuelve a la pantalla anterior sin apilarla de nuevo
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 8.dp)
            ) {
                Text("Volver al formulario")
            }


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val parts = listOfNotNull(
                        dniUri?.let { uriToMultipartPart(context, it, "postulante_dni") },
                        libretaUri?.let { uriToMultipartPart(context, it, "postulante_libreta_notas") },
                        constanciaUri?.let { uriToMultipartPart(context, it, "postulante_const_logro_aprendizaje") },
                        apoderadoDniUri?.let { uriToMultipartPart(context, it, "apoderado_dni") },
                        declaracionUri?.let { uriToMultipartPart(context, it, "apoderado_declaracion_jurada") },
                    )

                    viewModel.uploadArchivosPostulacionCompleta(
                        applicationId = applicationId,
                        archivos = parts,
                        onResult = { success, error ->
                            if (success) {
                                println("Archivos subidos correctamente")
                                navController.popBackStack() // O redirigir donde quieras
                            } else {
                                println("Error al subir archivos: $error")
                            }
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
            ) {
                Text("Subir Archivos PDF")
            }
        }
    }
}
