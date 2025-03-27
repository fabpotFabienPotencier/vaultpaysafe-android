package com.vaultpaysafe.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authInterceptor: AuthInterceptor
) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    
    private val securePrefs: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            "vault_pay_safe_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    
    fun saveToken(token: String) {
        securePrefs.edit().putString(Constants.PREF_AUTH_TOKEN, token).apply()
        authInterceptor.setToken(token)
    }
    
    fun getToken(): String? {
        return securePrefs.getString(Constants.PREF_AUTH_TOKEN, null)
    }
    
    fun saveUserId(userId: Int) {
        securePrefs.edit().putInt(Constants.PREF_USER_ID, userId).apply()
    }
    
    fun getUserId(): Int {
        return securePrefs.getInt(Constants.PREF_USER_ID, -1)
    }
    
    fun saveUserEmail(email: String) {
        securePrefs.edit().putString(Constants.PREF_USER_EMAIL, email).apply()
    }
    
    fun getUserEmail(): String? {
        return securePrefs.getString(Constants.PREF_USER_EMAIL, null)
    }
    
    fun saveUserName(name: String) {
        securePrefs.edit().putString(Constants.PREF_USER_NAME, name).apply()
    }
    
    fun getUserName(): String? {
        return securePrefs.getString(Constants.PREF_USER_NAME, null)
    }
    
    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
    
    fun logout() {
        securePrefs.edit().clear().apply()
        authInterceptor.clearToken()
    }
} 