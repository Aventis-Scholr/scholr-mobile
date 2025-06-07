package com.example.aventurape_androidmobile.shared.components

import android.webkit.WebView
import android.webkit.WebSettings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun YouTubeIframe(modifier: Modifier = Modifier) {
    val html = """
        <html>
        <body style="margin:0;">
            <iframe 
                width="100%" 
                height="100%" 
                src="https://www.youtube.com/embed/TbZ_hTEOKZc?si=390dR_qCybJ5ueis" 
                frameborder="0" 
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" 
                allowfullscreen
                referrerpolicy="strict-origin-when-cross-origin">
            </iframe>
        </body>
        </html>
    """.trimIndent()

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                loadDataWithBaseURL(
                    null,
                    html,
                    "text/html",
                    "utf-8",
                    null
                )
            }
        }
    )
}
