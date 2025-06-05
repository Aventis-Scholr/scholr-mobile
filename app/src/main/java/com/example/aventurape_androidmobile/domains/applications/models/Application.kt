package com.example.aventurape_androidmobile.domains.applications.models;

public class Application (
    val id: Int,
    val idApoderado: Int,
    val status: String,
    val tipoBeca: String,
    val postulante: Postulante
) {
    data class Postulante(
        val nombres: String,
        val apellidos: String,
        val dni: Int,
        val fechaNacimiento: String,
        val contacto: ContactoPostulante,
        val centroEstudios: CentroEstudios
    )

    data class ContactoPostulante(
        val correo: String,
        val celular: Int
    )

    data class CentroEstudios(
        val nombre: String,
        val tipo: String,
        val nivel: String,
        val departamento: String,
        val provincia: String,
        val distrito: String
    )
}
