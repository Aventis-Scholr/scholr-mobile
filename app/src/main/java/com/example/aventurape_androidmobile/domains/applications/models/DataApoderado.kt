package com.example.aventurape_androidmobile.domains.applications.models;

data class DataApoderado (
    val apoderadoId: Long,
    val nombres: String,
    val apellidos: String,
    val fechaNacimiento: String,
    val tipoDocumento: String,
    val numeroDocumento: String,
    val correo: String,
    val celular: String
)
