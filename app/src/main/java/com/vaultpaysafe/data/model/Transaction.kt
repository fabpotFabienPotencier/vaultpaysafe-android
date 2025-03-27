package com.vaultpaysafe.data.model

import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("transaction_id")
    val transactionId: Int,
    
    @SerializedName("from_account_id")
    val fromAccountId: Int?,
    
    @SerializedName("to_account_id")
    val toAccountId: Int?,
    
    @SerializedName("transaction_type")
    val transactionType: String,
    
    @SerializedName("amount")
    val amount: Double,
    
    @SerializedName("currency")
    val currency: String,
    
    @SerializedName("status")
    val status: String,
    
    @SerializedName("description")
    val description: String?,
    
    @SerializedName("fee_amount")
    val feeAmount: Double?,
    
    @SerializedName("created_at")
    val createdAt: String,
    
    @SerializedName("updated_at")
    val updatedAt: String?,
    
    @SerializedName("sender_account")
    val senderAccount: String?,
    
    @SerializedName("receiver_account")
    val receiverAccount: String?,
    
    @SerializedName("sender_email")
    val senderEmail: String?,
    
    @SerializedName("receiver_email")
    val receiverEmail: String?,
    
    @SerializedName("display_amount")
    val displayAmount: Double?
) 