package com.example.aventurape_androidmobile.domains.authentication.screens.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aventurape_androidmobile.domains.authentication.screens.states.SignupState
import com.example.aventurape_androidmobile.utils.RetrofitClient
import com.example.aventurape_androidmobile.utils.models.UserRequestSignUp


class SignUpViewModel(): ViewModel() {
    var state by mutableStateOf(SignupState())
        private set

    fun inputData(username: String, compania: String, dni: String, cod_colaborador:String,password: String,  confirmPassword: String) {
        state = state.copy(username = username,compania=compania,dni=dni,cod_colaborador=cod_colaborador, password = password, confirmPassword = confirmPassword)
    }
    fun resetStateExceptRole() {
        state = SignupState("","",state.role,"","","","",false,false, null)
    }
    fun SetCompany(compania: String){
        state = state.copy(compania = compania)
    }

    fun resetRole(){
        state = SignupState()
    }
    fun setRole(role: String) {
        state = state.copy(role = role)
    }

    fun validatePassword(password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            state = state.copy(signupSuccess = false, errorMessage = "Las contraseñas no coinciden")
        } else {
            state = state.copy(passwordEquals = true, errorMessage = null)
        }
    }

    suspend fun signUpUser(username: String, compania: String, dni: String, cod_colaborador:String, password: String) {
        val request = UserRequestSignUp(username = username,compania=compania,dni=dni,cod_colaborador=cod_colaborador, password = password, roles = listOf(state.role))
        val response = RetrofitClient.placeholder.singUp(request)
        if (response.isSuccessful && response.body() != null) {
            state = state.copy(signupSuccess = true, errorMessage = null)
        } else {
            state = state.copy(signupSuccess = false, errorMessage = "Error en el registro")
        }
    }
}