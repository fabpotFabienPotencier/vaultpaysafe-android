package com.vaultpaysafe.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultpaysafe.data.repository.UserRepository
import com.vaultpaysafe.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState: StateFlow<LoginState> = _loginState
    
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Initial)
    val registerState: StateFlow<RegisterState> = _registerState
    
    private val _forgotPasswordState = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Initial)
    val forgotPasswordState: StateFlow<ForgotPasswordState> = _forgotPasswordState
    
    private val _resetPasswordState = MutableStateFlow<ResetPasswordState>(ResetPasswordState.Initial)
    val resetPasswordState: StateFlow<ResetPasswordState> = _resetPasswordState
    
    private val _verifyEmailState = MutableStateFlow<VerifyEmailState>(VerifyEmailState.Initial)
    val verifyEmailState: StateFlow<VerifyEmailState> = _verifyEmailState
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginState.Loading
                
                val response = userRepository.login(email, password)
                
                if (response.isSuccess() && response.data != null) {
                    // Save user session data
                    response.data["token"]?.let { token ->
                        sessionManager.saveToken(token)
                    }
                    
                    response.data["user_id"]?.let { userId ->
                        sessionManager.saveUserId(userId.toInt())
                    }
                    
                    response.data["email"]?.let { email ->
                        sessionManager.saveUserEmail(email)
                    }
                    
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error(
                        response.error ?: response.message ?: "Unknown error occurred"
                    )
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }
    
    fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String? = null,
        country: String? = null,
        address: String? = null
    ) {
        viewModelScope.launch {
            try {
                _registerState.value = RegisterState.Loading
                
                val response = userRepository.register(
                    email, password, firstName, lastName, phone, country, address
                )
                
                if (response.isSuccess()) {
                    _registerState.value = RegisterState.Success
                } else {
                    _registerState.value = RegisterState.Error(
                        response.error ?: response.message ?: "Unknown error occurred"
                    )
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }
    
    fun forgotPassword(email: String) {
        viewModelScope.launch {
            try {
                _forgotPasswordState.value = ForgotPasswordState.Loading
                
                val response = userRepository.forgotPassword(email)
                
                if (response.isSuccess()) {
                    _forgotPasswordState.value = ForgotPasswordState.Success
                } else {
                    _forgotPasswordState.value = ForgotPasswordState.Error(
                        response.error ?: response.message ?: "Unknown error occurred"
                    )
                }
            } catch (e: Exception) {
                _forgotPasswordState.value = ForgotPasswordState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }
    
    fun resetPassword(email: String, code: String, newPassword: String) {
        viewModelScope.launch {
            try {
                _resetPasswordState.value = ResetPasswordState.Loading
                
                val response = userRepository.resetPassword(email, code, newPassword)
                
                if (response.isSuccess()) {
                    _resetPasswordState.value = ResetPasswordState.Success
                } else {
                    _resetPasswordState.value = ResetPasswordState.Error(
                        response.error ?: response.message ?: "Unknown error occurred"
                    )
                }
            } catch (e: Exception) {
                _resetPasswordState.value = ResetPasswordState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }
    
    fun verifyEmail(email: String, code: String) {
        viewModelScope.launch {
            try {
                _verifyEmailState.value = VerifyEmailState.Loading
                
                val response = userRepository.verifyEmail(email, code)
                
                if (response.isSuccess()) {
                    _verifyEmailState.value = VerifyEmailState.Success
                } else {
                    _verifyEmailState.value = VerifyEmailState.Error(
                        response.error ?: response.message ?: "Unknown error occurred"
                    )
                }
            } catch (e: Exception) {
                _verifyEmailState.value = VerifyEmailState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }
    
    fun logout() {
        userRepository.logout()
        sessionManager.logout()
    }
    
    // State classes
    sealed class LoginState {
        object Initial : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }
    
    sealed class RegisterState {
        object Initial : RegisterState()
        object Loading : RegisterState()
        object Success : RegisterState()
        data class Error(val message: String) : RegisterState()
    }
    
    sealed class ForgotPasswordState {
        object Initial : ForgotPasswordState()
        object Loading : ForgotPasswordState()
        object Success : ForgotPasswordState()
        data class Error(val message: String) : ForgotPasswordState()
    }
    
    sealed class ResetPasswordState {
        object Initial : ResetPasswordState()
        object Loading : ResetPasswordState()
        object Success : ResetPasswordState()
        data class Error(val message: String) : ResetPasswordState()
    }
    
    sealed class VerifyEmailState {
        object Initial : VerifyEmailState()
        object Loading : VerifyEmailState()
        object Success : VerifyEmailState()
        data class Error(val message: String) : VerifyEmailState()
    }
} 