package com.example.aventurape_androidmobile.utils

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val tokenProvider: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Obtén el token desde el proveedor
        val token = tokenProvider()

        if (token != null) {
            // Agrega el token al encabezado de autorización
            requestBuilder.addHeader("Authorization", "Bearer $token")
            android.util.Log.d("TokenInterceptor", "Token agregado a la petición: $token")
        }
        else {
            android.util.Log.d("TokenInterceptor", "No hay token para agregar")
        }

        return chain.proceed(requestBuilder.build())
    }
}