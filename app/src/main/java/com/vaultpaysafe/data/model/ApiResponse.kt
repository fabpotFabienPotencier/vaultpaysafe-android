package com.vaultpaysafe.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("status")
    val status: String,
    
    @SerializedName("message")
    val message: String?,
    
    @SerializedName("data")
    val data: T?,
    
    @SerializedName("error")
    val error: String?
) {
    fun isSuccess(): Boolean = status == "success"
} 