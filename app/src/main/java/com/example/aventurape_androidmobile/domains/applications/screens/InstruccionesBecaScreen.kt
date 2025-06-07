package com.example.aventurape_androidmobile.domains.applications.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstruccionesBecaScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cartilla de Instrucciones") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text("←")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("PROGRAMA DE BECAS PARA LOS HIJOS DE LOS COLABORADORES DE BACKUS Y SAN JUAN 2025 NIVEL ESCOLAR",
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            Text("I. INSTRUCCIONES GENERALES", fontWeight = FontWeight.Bold)
            Text("1. Es importante reconocer que el presente formulario web y los documentos adjuntos tienen validez de declaración jurada. Su veracidad será verificada en cualquier momento.")
            Text("2. La información se usará exclusivamente para evaluar el programa de becas.")
            Text("3. Completar el formulario dentro de los plazos establecidos.")
            Text("4. Adjuntar toda la documentación solicitada.")
            Text("5. Si se es beneficiario, la beca se abonará a la cuenta del colaborador. Por ello se solicita el número de cuenta, CCI y entidad bancaria.")
            Text("6. No se recibirán documentos fuera de fecha.")
            Text("7. Una vez enviada la solicitud, no se podrá modificar.")

            Spacer(modifier = Modifier.height(16.dp))

            Text("II. REQUISITOS PARA POSTULAR", fontWeight = FontWeight.Bold)
            Text("1. Hijos de colaboradores activos. Dependientes económicamente hasta los 25 años.")
            Text("2. Máximo 3 becas por colaborador.")
            Text("3. Niveles: Primaria y Secundaria en colegios nacionales, particulares, parroquiales o cooperativos.")
            Text("4. No haber desaprobado ningún curso en el año escolar evaluado.")
            Text("5. Promedio mínimo: Primaria: 16 o letra A / Secundaria: 15")
            Text("6. Quinto de secundaria deben adjuntar constancia de matrícula a una institución superior.")

            Spacer(modifier = Modifier.height(16.dp))

            Text("III. DOCUMENTOS PARA POSTULAR", fontWeight = FontWeight.Bold)
            Text("1. DNI del colaborador y del postulante (escaneado).")
            Text("2. Declaración jurada de uso de imagen y voz correctamente llenada, firmada y escaneada.")
            Text("3. Libreta de notas 2024 completa (sin borrones o enmendaduras).")
            Text("4. Constancia de Logro de Aprendizaje (CLA) 2024 sin borrones ni enmendaduras.")
            Text("   - Obtenerla desde: https://constancia.minedu.gob.pe/")
            Text("   - Guía en video: https://fb.watch/x57NuTsc7c/")
            Text("5. Constancia de ingreso y/o matrícula para egresados de secundaria 2024.")
            Text("6. Diplomas o certificados adicionales deben ser escaneados.")
            Text("7. Constancia de Orden de Mérito (si aplica: décimo/quinto/tercio superior).")

            Spacer(modifier = Modifier.height(16.dp))

            Text("IV. CRONOGRAMA", fontWeight = FontWeight.Bold)
            Text("Lanzamiento de convocatoria: Jueves 13 de febrero de 2025")
            Text("Cierre de convocatoria: Viernes 14 de marzo de 2025")

            Spacer(modifier = Modifier.height(16.dp))

            Text("CONSULTAS", fontWeight = FontWeight.Bold)
            Text("Teléfono: 7390570 anexos 107-109")
            Text("Horario de atención:")
            Text("   - Lunes a jueves: 9:00 a.m. - 1:00 p.m. / 2:00 p.m. - 5:30 p.m.")
            Text("   - Viernes: 9:00 a.m. - 1:30 p.m.")
        }
    }
}
