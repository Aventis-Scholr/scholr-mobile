package com.example.aventurape_androidmobile.utils.models

data class UserRequestSignUp(
    val username: String = "",
    val compania: String = "",
    val dni: String = "",
    val cod_colaborador: String = "",
    val password: String = "",
    val roles: List<String> = listOf()
)
