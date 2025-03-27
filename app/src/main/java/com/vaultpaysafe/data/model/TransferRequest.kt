package com.vaultpaysafe.data.model

import com.google.gson.annotations.SerializedName

data class TransferRequest(
    @SerializedName("from_account")
    val fromAccount: Int,
    
    @SerializedName("transfer_type")
    val transferType: String,
    
    @SerializedName("amount")
    val amount: Double,
    
    @SerializedName("description")
    val description: String?,
    
    // For internal transfers
    @SerializedName("recipient_account")
    val recipientAccount: String? = null,
    
    // For international transfers
    @SerializedName("recipient_country")
    val recipientCountry: String? = null,
    
    @SerializedName("bank_name")
    val bankName: String? = null,
    
    @SerializedName("swift_code")
    val swiftCode: String? = null,
    
    @SerializedName("recipient_name")
    val recipientName: String? = null,
    
    @SerializedName("recipient_address")
    val recipientAddress: String? = null
) 