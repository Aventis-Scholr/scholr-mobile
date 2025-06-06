package com.example.aventurape_androidmobile.domains.applications.models;

data class DataApoderado(
    val nombres: String,
    val apellidos: String,
    val dni: String,
    val tipoDocumento: String,
    val fechaNacimiento: String,
    val contacto: Contacto,
    val domicilio: Domicilio,
    val cuentaBancaria: CuentaBancaria,
    val informacionLaboral: InformacionLaboral
)