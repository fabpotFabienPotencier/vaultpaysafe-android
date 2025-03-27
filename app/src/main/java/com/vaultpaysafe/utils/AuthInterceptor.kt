package com.vaultpaysafe.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {
    
    private var authToken: String? = null
    
    fun setToken(token: String) {
        this.authToken = token
        Log.d("AuthInterceptor", "Token set")
    }
    
    fun clearToken() {
        this.authToken = null
        Log.d("AuthInterceptor", "Token cleared")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Skip adding auth header for authentication endpoints
        if (originalRequest.url.toString().contains("/auth/")) {
            return chain.proceed(originalRequest)
        }
        
        // If we have a token, add it to the request
        val modifiedRequest = authToken?.let { token ->
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } ?: originalRequest
        
        return chain.proceed(modifiedRequest)
    }
} 