package com.example.aventurape_androidmobile.domains.applications.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.domains.applications.models.Application
import com.example.aventurape_androidmobile.domains.applications.models.DataApoderado
import com.example.aventurape_androidmobile.domains.applications.states.HomeApplicationsState
import com.example.aventurape_androidmobile.utils.RetrofitClient
import kotlinx.coroutines.launch

import retrofit2.Retrofit
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory


class HomeApplicationsViewModel : ViewModel(){
    var state by mutableStateOf(HomeApplicationsState())
        private set

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

    fun loadDataApoderadoByApoderadoId(apoderadoId: Long) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.getDataApoderadoByApoderadoId(apoderadoId)
                if (response.isSuccessful) {
                    val dataApoderado = response.body()
                    state = state.copy(dataApoderado = dataApoderado, isLoading = false)
                } else {
                    state = state.copy(
                        errorMessage = "Error ${response.code()}: Failed to load data apoderado",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                state = state.copy(errorMessage = "Exception: ${e.localizedMessage}", isLoading = false)
            }
        }
    }


    fun saveDataApoderado(apoderadoId: Long, dataApoderado: DataApoderado) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.createDataApoderado(apoderadoId, dataApoderado)
                if (response.isSuccessful) {
                    state = state.copy(isLoading = false)
                } else {
                    state = state.copy(errorMessage = "Failed to save data apoderado", isLoading = false)
                }
            } catch (e: Exception) {
                state = state.copy(errorMessage = e.localizedMessage, isLoading = false)
            }
        }
    }

}