package com.vaultpaysafe.data.repository

import com.vaultpaysafe.data.model.ApiResponse
import com.vaultpaysafe.data.model.User
import com.vaultpaysafe.data.remote.VaultPaySafeApi
import com.vaultpaysafe.utils.AuthInterceptor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val api: VaultPaySafeApi,
    private val authInterceptor: AuthInterceptor
) {
    suspend fun login(email: String, password: String): ApiResponse<Map<String, String>> {
        return api.login(email, password).also { response ->
            if (response.isSuccess() && response.data?.containsKey("token") == true) {
                response.data["token"]?.let { token ->
                    authInterceptor.setToken(token)
                }
            }
        }
    }
    
    suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String? = null,
        country: String? = null,
        address: String? = null
    ): ApiResponse<Map<String, String>> {
        return api.register(email, password, firstName, lastName, phone, country, address)
    }
    
    suspend fun verifyEmail(email: String, code: String): ApiResponse<Map<String, String>> {
        return api.verifyEmail(email, code)
    }
    
    suspend fun forgotPassword(email: String): ApiResponse<Map<String, String>> {
        return api.forgotPassword(email)
    }
    
    suspend fun resetPassword(email: String, code: String, newPassword: String): ApiResponse<Map<String, String>> {
        return api.resetPassword(email, code, newPassword)
    }
    
    suspend fun getUserProfile(): ApiResponse<User> {
        return api.getUserProfile()
    }
    
    suspend fun updateUserProfile(
        firstName: String,
        lastName: String,
        phone: String,
        address: String
    ): ApiResponse<User> {
        return api.updateUserProfile(firstName, lastName, phone, address)
    }
    
    fun logout() {
        authInterceptor.clearToken()
    }
} 