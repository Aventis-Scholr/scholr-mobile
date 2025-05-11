package com.example.aventurape_androidmobile.domains.applications.models;

public class Application (
    val id: Int,
    val idApoderado: Int,
    val dataApoderado: DataApoderado,
    val status: String,
    val tipoBeca: String,
    val postulante: Postulante
) {
    data class DataApoderado(
        val id: Int,
        val createdAt: String,
        val updatedAt: String,
        val apoderadoId: Int,
        val nombres: String,
        val apellidos: String,
        val dni: Int,
        val fechaNacimiento: String,
        val contacto: Contacto,
        val domicilio: Domicilio,
        val cuentaBancaria: CuentaBancaria,
        val informacionLaboral: InformacionLaboral
    )

    data class Contacto(
        val correo: String,
        val celular: Int
    )

    data class Domicilio(
        val direccion: String,
        val departamento: String,
        val provincia: String,
        val distrito: String
    )

    data class CuentaBancaria(
        val entidadBancaria: String,
        val numeroCuenta: Int,
        val cci: Int
    )

    data class InformacionLaboral(
        val tipoColaborador: String,
        val cargo: String,
        val sede: String,
        val local: String,
        val ingreso: Int
    )

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
