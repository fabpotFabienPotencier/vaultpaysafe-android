package com.vaultpaysafe.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultpaysafe.data.model.Account
import com.vaultpaysafe.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadAccounts()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                
                val response = accountRepository.getAccounts()
                if (response.isSuccess() && response.data != null) {
                    _accounts.value = response.data.filter { it.status == "active" }
                } else {
                    _error.value = response.error ?: response.message ?: "Failed to load accounts"
                }
                
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = "Network error: ${e.localizedMessage}"
            }
        }
    }
} 