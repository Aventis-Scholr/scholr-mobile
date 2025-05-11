package com.example.aventurape_androidmobile.domains.applications.states

import com.example.aventurape_androidmobile.domains.applications.models.Application
import com.example.aventurape_androidmobile.domains.applications.models.DataApoderado

data class HomeApplicationsState (
    val applications: List<Application> = emptyList(),
    //val entrepreneurs: List<ProfileE> = emptyList(), // Nueva lista de emprendedores
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val dataApoderado: DataApoderado? = null,
)