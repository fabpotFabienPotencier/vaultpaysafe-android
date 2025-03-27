package com.vaultpaysafe.data.model

import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("account_id")
    val accountId: Int,
    
    @SerializedName("user_id")
    val userId: Int,
    
    @SerializedName("account_number")
    val accountNumber: String,
    
    @SerializedName("currency")
    val currency: String,
    
    @SerializedName("account_type")
    val accountType: String,
    
    @SerializedName("status")
    val status: String,
    
    @SerializedName("created_at")
    val createdAt: String,
    
    @SerializedName("current_balance")
    val currentBalance: Double,
    
    @SerializedName("available_balance")
    val availableBalance: Double,
    
    @SerializedName("total_transactions")
    val totalTransactions: Int?,
    
    @SerializedName("last_transaction")
    val lastTransaction: String?,
    
    @SerializedName("card_count")
    val cardCount: Int?
) 