package com.example.aventurape_androidmobile.domains.applications.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.domains.applications.models.Application
import com.example.aventurape_androidmobile.domains.applications.models.Contacto
import com.example.aventurape_androidmobile.domains.applications.models.CuentaBancaria
import com.example.aventurape_androidmobile.domains.applications.models.DataApoderado
import com.example.aventurape_androidmobile.domains.applications.models.Domicilio
import com.example.aventurape_androidmobile.domains.applications.models.InformacionLaboral
import com.example.aventurape_androidmobile.domains.applications.states.HomeApplicationsState
import com.example.aventurape_androidmobile.utils.RetrofitClient
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import java.text.ParseException
import retrofit2.Retrofit
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory


class HomeApplicationsViewModel : ViewModel(){
    var state by mutableStateOf(HomeApplicationsState())
        private set

    // Campos del formulario (uno por cada dato del apoderado)
    var nombres by mutableStateOf("")
    var apellidos by mutableStateOf("")
    var fechaNacimiento by mutableStateOf("")
    var tipoDocumento by mutableStateOf("")
    var numeroDocumento by mutableStateOf("")
    var correo by mutableStateOf("")
    var celular by mutableStateOf("")

    // Cuenta bancaria
    var entidadBancaria by mutableStateOf("")
    var numeroCuenta by mutableStateOf("")
    var cci by mutableStateOf("")

    // Domicilio
    var direccion by mutableStateOf("")
    var departamento by mutableStateOf("")
    var provincia by mutableStateOf("")
    var distrito by mutableStateOf("")

    // Información laboral
    var tipoColaborador by mutableStateOf("")
    var cargo by mutableStateOf("")
    var sede by mutableStateOf("")
    var local by mutableStateOf("")
    var ingreso by mutableStateOf("")

    // Términos y Condiciones
    var acepto by mutableStateOf(false)


    fun formatFechaNacimiento(fechaIso: String): String {
        return try {
            val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
            val date: Date? = isoFormat.parse(fechaIso)
            val desiredFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            if (date != null) desiredFormat.format(date) else ""
        } catch (e: ParseException) {
            ""
        }
    }

    fun loadApplications() {

        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.getAllApplications() // Assume API call setup
                if (response.isSuccessful) {
                    val applications = response.body() ?: emptyList()
                    state = state.copy(applications = applications, isLoading = false)
                } else {
                    state = state.copy(errorMessage = "Failed to load applications", isLoading = false)
                }
            } catch (e: Exception) {
                state = state.copy(errorMessage = e.localizedMessage, isLoading = false)
            }
        }
    }

    //data apoderado--------------------------------

    // ================== CARGAR DATOS DEL APODERADO ==================
    fun loadDataApoderadoByApoderadoId(apoderadoId: Long) {
        Log.d("HomeApplicationsVM", "Token antes de la llamada: ${RetrofitClient.getToken()}")
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.getDataApoderadoByApoderadoId(apoderadoId)
                if (response.isSuccessful) {
                    val dataApoderado = response.body()
                    Log.d("HomeApplicationsVM", "Respuesta backend: $dataApoderado")
                    dataApoderado?.let {
                        nombres = it.nombres
                        apellidos = it.apellidos
                        fechaNacimiento = formatFechaNacimiento(it.fechaNacimiento)
                        numeroDocumento = it.dni.toString()
                        tipoDocumento = it.tipoDocumento ?: ""
                        correo = it.contacto.correo
                        celular = it.contacto.celular.toString()

                        entidadBancaria = it.cuentaBancaria.entidadBancaria
                        numeroCuenta = it.cuentaBancaria.numeroCuenta.toString()
                        cci = it.cuentaBancaria.cci.toString()

                        direccion = it.domicilio.direccion
                        departamento = it.domicilio.departamento
                        provincia = it.domicilio.provincia
                        distrito = it.domicilio.distrito

                        tipoColaborador = it.informacionLaboral.tipoColaborador
                        cargo = it.informacionLaboral.cargo
                        sede = it.informacionLaboral.sede
                        local = it.informacionLaboral.local
                        ingreso = it.informacionLaboral.ingreso.toString()

                        Log.d("HomeApplicationsVM", "Datos asignados: nombres=$nombres, apellidos=$apellidos, fechaNacimiento=$fechaNacimiento")
                    }
                    state = state.copy(dataApoderado = dataApoderado, isLoading = false)
                } else {
                    Log.e("HomeApplicationsVM", "Error en la respuesta: ${response.code()}")
                    state = state.copy(errorMessage = "Error ${response.code()}", isLoading = false)
                }
            } catch (e: Exception) {
                Log.e("HomeApplicationsVM", "Excepción: ${e.localizedMessage}")
                state = state.copy(errorMessage = "Exception: ${e.localizedMessage}", isLoading = false)
            }
        }
    }

    // ================== GUARDAR DATOS DEL APODERADO ==================
    fun saveDataApoderado(
        apoderadoId: Long,
        nombres: String,
        apellidos: String,
        fechaNacimiento: String,
        dni: String,
        tipoDocumento: String,
        contacto: Contacto,
        domicilio: Domicilio,
        cuentaBancaria: CuentaBancaria,
        informacionLaboral: InformacionLaboral
    ) {
        val dataApoderado = DataApoderado(
            nombres = nombres,
            apellidos = apellidos,
            dni = dni,
            tipoDocumento = tipoDocumento,
            fechaNacimiento = fechaNacimiento,
            contacto = contacto,
            domicilio = domicilio,
            cuentaBancaria = cuentaBancaria,
            informacionLaboral = informacionLaboral
        )

        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.createDataApoderado(apoderadoId, dataApoderado)
                if (response.isSuccessful) {
                    state = state.copy(isLoading = false)
                } else {
                    state = state.copy(errorMessage = "Error al guardar datos", isLoading = false)
                }
            } catch (e: Exception) {
                state = state.copy(errorMessage = e.localizedMessage, isLoading = false)
            }
        }
    }
}