package com.example.aventurape_androidmobile.domains.management.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import com.example.aventurape_androidmobile.domains.management.screens.viewModels.HomeScholarshipsViewModel
import com.example.aventurape_androidmobile.shared.components.TopBar

@Composable
fun HomeScholarshipsScreen(viewModel: HomeScholarshipsViewModel, navController: NavController) {
    LaunchedEffect(Unit) {
        viewModel.loadScholarships()
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar()
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Becas de la empresa",
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
            )

            //Lista de Becas
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                
            }
        }
    }
}