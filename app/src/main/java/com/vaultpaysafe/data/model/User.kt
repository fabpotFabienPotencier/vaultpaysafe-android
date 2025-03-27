package com.vaultpaysafe.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val userId: Int,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("first_name")
    val firstName: String,
    
    @SerializedName("last_name")
    val lastName: String,
    
    @SerializedName("phone")
    val phone: String?,
    
    @SerializedName("country")
    val country: String?,
    
    @SerializedName("address")
    val address: String?,
    
    @SerializedName("status")
    val status: String,
    
    @SerializedName("created_at")
    val createdAt: String,
    
    @SerializedName("last_login")
    val lastLogin: String?
) 