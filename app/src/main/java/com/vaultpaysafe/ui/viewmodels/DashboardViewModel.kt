package com.vaultpaysafe.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultpaysafe.data.model.Account
import com.vaultpaysafe.data.model.Transaction
import com.vaultpaysafe.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadDashboardData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                // Load accounts
                val accountsResponse = accountRepository.getAccounts()
                if (accountsResponse.isSuccess() && accountsResponse.data != null) {
                    _accounts.value = accountsResponse.data
                } else {
                    _error.value = accountsResponse.error ?: accountsResponse.message ?: "Failed to load accounts"
                    _accounts.value = emptyList()
                }

                // Load recent transactions
                val transactionsResponse = accountRepository.getTransactions(limit = 10)
                if (transactionsResponse.isSuccess() && transactionsResponse.data != null) {
                    _transactions.value = transactionsResponse.data
                } else {
                    // Don't show error for transactions, just show an empty state
                    _transactions.value = emptyList()
                }

                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = "Network error: ${e.localizedMessage}"
                _accounts.value = emptyList()
                _transactions.value = emptyList()
            }
        }
    }
} 