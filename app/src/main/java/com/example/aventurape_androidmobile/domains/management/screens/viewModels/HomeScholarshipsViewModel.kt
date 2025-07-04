package com.example.aventurape_androidmobile.domains.management.screens.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aventurape_androidmobile.domains.management.screens.states.HomeScholarshipsState
import com.example.aventurape_androidmobile.utils.RetrofitClient
import kotlinx.coroutines.launch

class HomeScholarshipsViewModel : ViewModel() {
    var state by mutableStateOf(HomeScholarshipsState())
        private set

    fun loadScholarships() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.getAllScholarships()
                if (response.isSuccessful) {
                    val scholarships = response.body() ?: emptyList()
                    state = state.copy(scholarships = scholarships, isLoading = false)
                } else {
                    state = state.copy(errorMessage = "Failed to load scholarships", isLoading = false)
                }
            } catch (e: Exception) {
                state = state.copy(errorMessage = e.localizedMessage, isLoading = false)
            }
        }
    }


}