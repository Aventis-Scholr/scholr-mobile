package com.example.aventurape_androidmobile.domains.management.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aventurape_androidmobile.domains.management.models.Scholarship
import com.example.aventurape_androidmobile.domains.management.screens.viewModels.HomeScholarshipsViewModel
import com.example.aventurape_androidmobile.shared.components.TopBar

@Composable
fun HomeScholarshipsScreen(viewModel: HomeScholarshipsViewModel, navController: NavController) {
    LaunchedEffect(Unit) {
        viewModel.loadScholarships()
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(onOpenDrawer = {})
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Becas de la empresa",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(viewModel.state.scholarships) { scholarship ->
                    ScholarshipCard(scholarship, navController)
                }
            }
        }
    }
}

@Composable
fun ScholarshipCard(scholarship: Scholarship, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFE3F2FD))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = scholarship.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tipo: ${scholarship.scholarshipType}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Estado: ${scholarship.scholarshipStatus}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}