package com.example.aventurape_androidmobile.domains.applications.models;

data class DataApoderado (
    val nombres: String,
    val apellidos: String,
    val dni: Int,
    val fechaNacimiento: String,

    val contacto: Contacto,
    val domicilio: Domicilio,
    val cuentaBancaria: CuentaBancaria,
    val informacionLaboral: InformacionLaboral,
)
{
    data class Contacto(
        val correo: String,
        val celular: Int
    )

    data class Domicilio(
        val direccion: String,
        val departamento: String,
        val provincia: String,
        val distrito: String,
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
}
