package com.vaultpaysafe.data.repository

import com.vaultpaysafe.data.model.Account
import com.vaultpaysafe.data.model.ApiResponse
import com.vaultpaysafe.data.model.Transaction
import com.vaultpaysafe.data.model.TransferRequest
import com.vaultpaysafe.data.remote.VaultPaySafeApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(
    private val api: VaultPaySafeApi
) {
    suspend fun getAccounts(): ApiResponse<List<Account>> {
        return api.getAccounts()
    }
    
    suspend fun getAccountDetails(accountId: Int): ApiResponse<Account> {
        return api.getAccountDetails(accountId)
    }
    
    suspend fun getTransactions(accountId: Int? = null, page: Int = 1, limit: Int = 20): ApiResponse<List<Transaction>> {
        return api.getTransactions(accountId, page, limit)
    }
    
    suspend fun getTransactionDetails(transactionId: Int): ApiResponse<Transaction> {
        return api.getTransactionDetails(transactionId)
    }
    
    suspend fun createTransfer(transferRequest: TransferRequest): ApiResponse<Transaction> {
        return api.createTransfer(transferRequest)
    }
    
    suspend fun getExchangeRate(fromCurrency: String, toCurrency: String): ApiResponse<Map<String, Double>> {
        return api.getExchangeRate(fromCurrency, toCurrency)
    }
    
    suspend fun validateAccount(accountNumber: String): ApiResponse<Map<String, String>> {
        return api.validateAccount(accountNumber)
    }
} 