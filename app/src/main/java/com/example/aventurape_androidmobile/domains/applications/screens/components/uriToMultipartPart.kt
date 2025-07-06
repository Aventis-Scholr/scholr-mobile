package com.example.aventurape_androidmobile.domains.applications.screens.components

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

fun uriToMultipartPart(
    context: Context,
    uri: Uri,
    partName: String
): MultipartBody.Part? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val tempFile = File.createTempFile("upload_", ".pdf", context.cacheDir)
        val outputStream = FileOutputStream(tempFile)

        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()

        val requestFile = tempFile
            .asRequestBody("application/pdf".toMediaTypeOrNull())

        MultipartBody.Part.createFormData(partName, tempFile.name, requestFile)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
