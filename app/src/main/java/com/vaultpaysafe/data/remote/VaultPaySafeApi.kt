package com.vaultpaysafe.data.remote

import com.vaultpaysafe.data.model.*
import retrofit2.http.*

interface VaultPaySafeApi {
    // Authentication
    @POST("auth/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): ApiResponse<Map<String, String>>
    
    @POST("auth/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("phone") phone: String?,
        @Field("country") country: String?,
        @Field("address") address: String?
    ): ApiResponse<Map<String, String>>
    
    @POST("auth/verify-email")
    @FormUrlEncoded
    suspend fun verifyEmail(
        @Field("email") email: String,
        @Field("code") code: String
    ): ApiResponse<Map<String, String>>
    
    @POST("auth/forgot-password")
    @FormUrlEncoded
    suspend fun forgotPassword(
        @Field("email") email: String
    ): ApiResponse<Map<String, String>>
    
    @POST("auth/reset-password")
    @FormUrlEncoded
    suspend fun resetPassword(
        @Field("email") email: String,
        @Field("code") code: String,
        @Field("new_password") newPassword: String
    ): ApiResponse<Map<String, String>>
    
    // User Profile
    @GET("api/users/profile")
    suspend fun getUserProfile(): ApiResponse<User>
    
    @PUT("api/users/profile")
    @FormUrlEncoded
    suspend fun updateUserProfile(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("phone") phone: String,
        @Field("address") address: String
    ): ApiResponse<User>
    
    // Accounts
    @GET("api/accounts")
    suspend fun getAccounts(): ApiResponse<List<Account>>
    
    @GET("api/accounts/{accountId}")
    suspend fun getAccountDetails(
        @Path("accountId") accountId: Int
    ): ApiResponse<Account>
    
    // Transactions
    @GET("api/transactions")
    suspend fun getTransactions(
        @Query("account_id") accountId: Int? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): ApiResponse<List<Transaction>>
    
    @GET("api/transactions/{transactionId}")
    suspend fun getTransactionDetails(
        @Path("transactionId") transactionId: Int
    ): ApiResponse<Transaction>
    
    // Transfers
    @POST("api/transfer")
    suspend fun createTransfer(
        @Body transferRequest: TransferRequest
    ): ApiResponse<Transaction>
    
    @GET("api/get-exchange-rate")
    suspend fun getExchangeRate(
        @Query("from_currency") fromCurrency: String,
        @Query("to_currency") toCurrency: String
    ): ApiResponse<Map<String, Double>>
    
    @GET("api/validate-account")
    suspend fun validateAccount(
        @Query("account_number") accountNumber: String
    ): ApiResponse<Map<String, String>>
} 