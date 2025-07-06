package com.example.aventurape_androidmobile.domains.applications.viewModels

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aventurape_androidmobile.domains.applications.models.Application
import com.example.aventurape_androidmobile.domains.applications.models.ApplicationRequest
import com.example.aventurape_androidmobile.domains.applications.models.DataApoderado
import com.example.aventurape_androidmobile.domains.applications.states.HomeApplicationsState
import com.example.aventurape_androidmobile.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeApplicationsViewModel : ViewModel(){

    // Estado del formulario
    var nameInput = mutableStateOf("")
    var lastNamesInput = mutableStateOf("")
    var dniInput = mutableStateOf("")
    var birthdayInput = mutableStateOf("")
    var emailInput = mutableStateOf("")
    var phoneInput = mutableStateOf("")
    var schoolNameInput = mutableStateOf("")
    var schoolTypeInput = mutableStateOf("")
    var schoolLevelInput = mutableStateOf("")
    var schoolDepartmentInput = mutableStateOf("")
    var schoolProvinceInput = mutableStateOf("")
    var schoolDistrictInput = mutableStateOf("")
    var scholarshipTypeInput = mutableStateOf("")

    val postulanteDniUri = mutableStateOf<Uri?>(null)
    val postulanteLibretaNotasUri = mutableStateOf<Uri?>(null)
    val postulanteConstLogroAprendizajeUri = mutableStateOf<Uri?>(null)
    val apoderadoDniUri = mutableStateOf<Uri?>(null)
    val apoderadoDeclaracionJuradaUri = mutableStateOf<Uri?>(null)

    var state by mutableStateOf(HomeApplicationsState())
        private set

    var createApplicationSuccess by mutableStateOf(false)
        private set

    var deleteApplicationSuccess by mutableStateOf(false)
        private  set

    var updateApplicationSuccess by mutableStateOf(false)
        private  set

    lateinit var applicationToEdit: Application
    //-------------------------------------------------

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
    fun createApplication(
        application: ApplicationRequest,
        apoderadoId: Long,
        onSuccessNavigation: (Long) -> Unit
    ) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.createApplication(application, apoderadoId)
                if (response.isSuccessful) {
                    val createdApplication = response.body()
                    if (createdApplication != null) {
                        applicationToEdit = createdApplication
                        createApplicationSuccess = true
                        state = state.copy(isLoading = false)
                        // Ejecutar navegación en hilo principal
                        withContext(Dispatchers.Main) {
                            onSuccessNavigation(createdApplication.id.toLong())
                        }
                    } else {
                        state = state.copy(errorMessage = "Respuesta vacía del servidor", isLoading = false)
                    }
                } else {
                    state = state.copy(
                        errorMessage = "Error ${response.code()}: no se pudo crear la postulación",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                state = state.copy(
                    errorMessage = "Excepción: ${e.localizedMessage}",
                    isLoading = false
                )
            }
        }
    }

    fun updateApplication(application: ApplicationRequest, id: Long) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.updateApplication(application, id)
                if (response.isSuccessful) {
                    updateApplicationSuccess = true
                    state = state.copy(isLoading = false)
                    getApplicationsByApoderadoId(application.idApoderado.toLong())
                } else {
                    state = state.copy(
                        errorMessage = "Error ${response.code()}: Failed to update application",
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

    // ================== ATOS DEL APODERADO ==================

    fun createDataApoderado(dataApoderado: DataApoderado, apoderadoId: Long) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.createDataApoderado(apoderadoId, dataApoderado)
                if (response.isSuccessful) {
                    state = state.copy(isLoading = false)
                } else {
                    state = state.copy(errorMessage = "Error al crear datos del apoderado", isLoading = false)
                }
            } catch (e: Exception) {
                state = state.copy(errorMessage = e.localizedMessage, isLoading = false)
            }
        }
    }


    //cargar datos y almacenar campos para el formulario
    fun getDataApoderadoByApoderadoId(apoderadoId: Long){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.getDataApoderadoByApoderadoId(apoderadoId)
                if (response.isSuccessful) {
                    val dataApoderado = response.body()
                    state = state.copy(dataApoderado = dataApoderado, isLoading = false)
                } else {
                    state = state.copy(dataApoderado = null, errorMessage = "Failed to load data apoderado", isLoading = false)
                }
            } catch (e: Exception) {
                state = state.copy(errorMessage = e.localizedMessage, isLoading = false)
            }
        }
    }

    //update datos del apoderado
    fun updateDataApoderado(dataApoderado: DataApoderado, apoderadoId: Long) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.updateDataApoderado(apoderadoId, dataApoderado)
                if (response.isSuccessful) {
                    state = state.copy(isLoading = false)
                } else {
                    state = state.copy(errorMessage = "Error al actualizar datos del apoderado", isLoading = false)
                }
            } catch (e: Exception) {
                state = state.copy(errorMessage = e.localizedMessage, isLoading = false)
            }
        }
    }

    //SUBIR ARCHIVOS
    fun uploadApplicationFiles(
        applicationId: Long,
        postulanteDni: MultipartBody.Part,
        postulanteLibretaNotas: MultipartBody.Part,
        postulanteConstLogroAprendizaje: MultipartBody.Part,
        apoderadoDni: MultipartBody.Part,
        apoderadoDeclaracionJurada: MultipartBody.Part,
        onResult: (Boolean, String?) -> Unit
    ) {
        val call = RetrofitClient.placeholder.uploadApplicationFiles(
            applicationId,
            postulanteDni,
            postulanteLibretaNotas,
            postulanteConstLogroAprendizaje,
            apoderadoDni,
            apoderadoDeclaracionJurada
        )

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, "Error al subir archivos")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(false, t.localizedMessage)
            }
        })
    }

    fun uploadArchivosPostulacionCompleta(
        applicationId: Long,
        archivos: List<MultipartBody.Part>,
        onResult: (Boolean, String?) -> Unit
    ) {
        val call = RetrofitClient.placeholder.uploadArchivosPostulacionCompleta(applicationId, archivos)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, "Error ${response.code()}: al subir archivos")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(false, t.localizedMessage ?: "Error desconocido al subir archivos")
            }
        })
    }
}