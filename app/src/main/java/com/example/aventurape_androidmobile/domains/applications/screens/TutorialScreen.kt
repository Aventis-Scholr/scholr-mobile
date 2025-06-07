package com.example.aventurape_androidmobile.domains.applications.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.domains.applications.viewModels.HomeApplicationsViewModel
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer
import com.example.aventurape_androidmobile.shared.components.Drawer
import com.example.aventurape_androidmobile.shared.components.TopBar
import com.example.aventurape_androidmobile.shared.components.YouTubeIframe
import kotlinx.coroutines.launch
@Composable
fun TutorialScreen(viewModel: HomeApplicationsViewModel, navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Drawer(navController)
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(onOpenDrawer = {
                    scope.launch {
                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                    }
                })
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                // Primera sección: Video tutorial
                Text(
                    text = "\uD83D\uDCFD️ Video Tutorial",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                YouTubeIframe(videoId = "dQw4w9WgXcQ")

                Spacer(modifier = Modifier.height(32.dp))

                // Segunda sección: (pendiente)
                Text(
                    text = "📄 Instrucciones de la Beca",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Revisa aquí los requisitos y cronograma para postular a la beca.",
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Button(
                    onClick = { navController.navigate(NavScreenAdventurer.cartilla_instrucciones.name) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                ) {
                    Text("Ver Cartilla de Instrucciones")
                }

                // Tercera sección: Contacto
                Text(
                    text = "\uD83D\uDCDE Contáctanos",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(text = "\uD83D\uDCE7 Gmail: contacto@aventurape.com")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "\uD83D\uDCF1 WhatsApp: +51 987 654 321")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "\uD83D\uDCF8 Instagram: @aventurape")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "\uD83C\uDFB5 TikTok: @aventurape")
                }
            }
        }
    }
}

