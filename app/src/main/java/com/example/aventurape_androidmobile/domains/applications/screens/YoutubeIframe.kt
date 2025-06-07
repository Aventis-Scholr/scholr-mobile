package com.example.aventurape_androidmobile.domains.applications.screens

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth

@Composable
fun YouTubeIframe(videoId: String) {
    val videoUrl = "https://www.youtube.com/embed/$videoId"
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                loadUrl(videoUrl)
            }
        }
    )
}
