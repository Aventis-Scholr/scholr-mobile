package com.example.aventurape_androidmobile.domains.authentication.screens.states

import com.example.aventurape_androidmobile.domains.authentication.models.UserLogged

data class SignupState (
    val username: String = "",
    val compania: String = "",
    val dni: String = "",
    val cod_colaborador: String = "",
    val role: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordEquals: Boolean = false,
    val signupSuccess: Boolean = false,
    val errorMessage: String? = null, // Dejarlo en null cuando no hay error
)