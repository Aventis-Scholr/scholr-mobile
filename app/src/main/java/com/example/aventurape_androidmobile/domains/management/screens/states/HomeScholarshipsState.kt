package com.example.aventurape_androidmobile.domains.management.screens.states

import com.example.aventurape_androidmobile.domains.management.models.Scholarship

data class HomeScholarshipsState(
    val companyName: String = "Backus",
    val scholarships: List<Scholarship> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)