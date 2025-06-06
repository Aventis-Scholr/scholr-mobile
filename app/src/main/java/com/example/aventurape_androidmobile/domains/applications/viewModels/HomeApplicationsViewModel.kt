package com.example.aventurape_androidmobile.domains.applications.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aventurape_androidmobile.domains.applications.models.Application
import com.example.aventurape_androidmobile.domains.applications.models.DataApoderado
import com.example.aventurape_androidmobile.domains.applications.states.HomeApplicationsState
import com.example.aventurape_androidmobile.utils.RetrofitClient
import kotlinx.coroutines.launch


class HomeApplicationsViewModel : ViewModel(){
    var state by mutableStateOf(HomeApplicationsState())
        private set

    var createApplicationSuccess by mutableStateOf(false)
        private set

    var deleteApplicationSuccess by mutableStateOf(false)
        private  set

    // Campos del formulario (uno por cada dato del apoderado)
    var nombres by mutableStateOf("")
    var apellidos by mutableStateOf("")
    var fechaNacimiento by mutableStateOf("")
    var tipoDocumento by mutableStateOf("")
    var numeroDocumento by mutableStateOf("")
    var correo by mutableStateOf("")
    var celular by mutableStateOf("")

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

    fun getApplicationsByApoderadoId(apoderadoId: Long) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.getApplicationsByApoderadoId(apoderadoId)
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

    // Función para crear una nueva aplicación
    fun createApplication(application: Application, apoderadoId: Long) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.createApplication(application, apoderadoId)
                if (response.isSuccessful) {
                    createApplicationSuccess = true
                    state = state.copy(isLoading = false)
                    getApplicationsByApoderadoId(application.idApoderado.toLong())
                } else {
                    state = state.copy(
                        errorMessage = "Error ${response.code()}: Failed to create application",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                state = state.copy(
                    errorMessage = "Exception: ${e.localizedMessage}",
                    isLoading = false
                )
            }
        }
    }

    fun deleteApplication(id: Long, idApoderado: Long) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.deleteApplication(id)
                if (response.isSuccessful) {
                    deleteApplicationSuccess = true
                    state = state.copy(isLoading = false)
                    getApplicationsByApoderadoId(idApoderado)
                } else {
                    state = state.copy(
                        errorMessage = "Error ${response.code()}: Failed to delete application",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                state = state.copy(
                    errorMessage = "Exception: ${e.localizedMessage}",
                    isLoading = false
                )
            }
        }
    }

    //data apoderado--------------------------------

    // ================== CARGAR DATOS DEL APODERADO ==================
    fun loadDataApoderadoByApoderadoId(apoderadoId: Long) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.getDataApoderadoByApoderadoId(apoderadoId)
                if (response.isSuccessful) {
                    val dataApoderado = response.body()
                    dataApoderado?.let {
                        // Llenar campos del formulario con los datos obtenidos
                        nombres = it.nombres ?: ""
                        apellidos = it.apellidos ?: ""
                        fechaNacimiento = it.fechaNacimiento ?: ""
                        tipoDocumento = it.tipoDocumento ?: ""
                        numeroDocumento = it.numeroDocumento ?: ""
                        correo = it.correo ?: ""
                        celular = it.celular ?: ""
                    }
                    state = state.copy(dataApoderado = dataApoderado, isLoading = false)
                } else {
                    state = state.copy(errorMessage = "Error ${response.code()}", isLoading = false)
                }
            } catch (e: Exception) {
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
        tipoDocumento: String,
        numeroDocumento: String,
        correo: String,
        celular: String
    ) {
        val dataApoderado = DataApoderado(
            apoderadoId = apoderadoId,
            nombres = nombres,
            apellidos = apellidos,
            fechaNacimiento = fechaNacimiento,
            tipoDocumento = tipoDocumento,
            numeroDocumento = numeroDocumento,
            correo = correo,
            celular = celular
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