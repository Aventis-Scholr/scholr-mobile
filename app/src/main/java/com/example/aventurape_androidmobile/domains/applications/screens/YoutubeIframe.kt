package com.example.aventurape_androidmobile.domains.applications.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height

@Composable
fun YouTubeIframe(videoId: String) {
    val context = LocalContext.current
    val videoUrl = "https://www.youtube.com/watch?v=$videoId"

    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
            context.startActivity(intent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text("Ver Video en YouTube")
    }
}
