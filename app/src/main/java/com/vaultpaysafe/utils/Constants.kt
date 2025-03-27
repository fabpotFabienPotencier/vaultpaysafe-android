package com.vaultpaysafe.utils

object Constants {
    // API Configuration
    const val API_BASE_URL = "https://www.vaultpaysafeplc.com/vaultpaysafe/"
    
    // Preferences Keys
    const val PREF_AUTH_TOKEN = "auth_token"
    const val PREF_USER_ID = "user_id"
    const val PREF_USER_EMAIL = "user_email"
    const val PREF_USER_NAME = "user_name"
    
    // Transaction Types
    const val TRANSACTION_TYPE_TRANSFER = "transfer"
    const val TRANSACTION_TYPE_DEPOSIT = "deposit"
    const val TRANSACTION_TYPE_WITHDRAWAL = "withdrawal"
    
    // Transaction Status
    const val TRANSACTION_STATUS_COMPLETED = "completed"
    const val TRANSACTION_STATUS_PENDING = "pending"
    const val TRANSACTION_STATUS_FAILED = "failed"
    
    // Transfer Types
    const val TRANSFER_TYPE_INTERNAL = "internal"
    const val TRANSFER_TYPE_INTERNATIONAL = "international"
    
    // Bundle Keys
    const val KEY_ACCOUNT_ID = "account_id"
    const val KEY_TRANSACTION_ID = "transaction_id"
    
    // Biometric
    const val BIOMETRIC_PROMPT_TITLE = "Authentication Required"
    const val BIOMETRIC_PROMPT_SUBTITLE = "Verify your identity to continue"
    const val BIOMETRIC_PROMPT_CANCEL = "Cancel"
} 