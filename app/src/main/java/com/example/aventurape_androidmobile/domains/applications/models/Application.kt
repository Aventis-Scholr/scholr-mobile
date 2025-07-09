package com.example.aventurape_androidmobile.domains.applications.models;

public class Application (
    val id: Int,
    val idApoderado: Int,
    val status: String,
    val scholarshipId: Int,
    val postulante: Postulante,
    val postulante_dni: String,
    val postulante_libreta_notas: String,
    val postulante_const_logro_aprendizaje: String,
    val apoderado_dni: String,
    val apoderado_declaracion_jurada: String,
    val reporte: String?
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
