package com.example.aventurape_androidmobile.domains.applications.screens.components

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ArchivoUploadField(
    label: String,
    uriSeleccionado: Uri?,
    onFileSelected: (Uri, String) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val fileName = getFileNameFromUri(context, it)
            onFileSelected(it, fileName)
        }
    }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, fontWeight = FontWeight.Medium)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = uriSeleccionado?.let {
                    getFileNameFromUri(context, it)
                } ?: "Ningún archivo seleccionado",
                modifier = Modifier.weight(1f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { launcher.launch("application/pdf") }) {
                Text("Seleccionar")
            }
        }
    }
}

fun getFileNameFromUri(context: Context, uri: Uri): String {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    return cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        it.getString(nameIndex)
    } ?: uri.lastPathSegment ?: "archivo.pdf"
}