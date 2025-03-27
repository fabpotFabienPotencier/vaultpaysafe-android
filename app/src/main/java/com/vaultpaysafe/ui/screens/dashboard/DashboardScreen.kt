package com.vaultpaysafe.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vaultpaysafe.R
import com.vaultpaysafe.data.model.Account
import com.vaultpaysafe.data.model.Transaction
import com.vaultpaysafe.ui.viewmodels.DashboardViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToTransfer: () -> Unit,
    onNavigateToTransactions: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onLogout: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val accountsState by viewModel.accounts.collectAsState()
    val transactionsState by viewModel.transactions.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.loadDashboardData()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.dashboard)) },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                NavigationBar {
                    NavigationBarItem(
                        selected = true,
                        onClick = { },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text(stringResource(R.string.dashboard)) }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = onNavigateToTransactions,
                        icon = { Icon(Icons.Default.List, contentDescription = "Transactions") },
                        label = { Text(stringResource(R.string.transactions)) }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = onNavigateToTransfer,
                        icon = { Icon(Icons.Default.Send, contentDescription = "Transfer") },
                        label = { Text(stringResource(R.string.transfer)) }
                    )
                }
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = error ?: stringResource(R.string.error_occurred),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(onClick = { viewModel.loadDashboardData() }) {
                        Text(stringResource(R.string.try_again))
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    QuickActions(onNavigateToTransfer)
                }
                
                item {
                    Text(
                        text = stringResource(R.string.accounts),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                item {
                    if (accountsState.isEmpty()) {
                        EmptyState(
                            text = "You don't have any accounts yet",
                            icon = Icons.Default.AccountBalance
                        )
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(accountsState) { account ->
                                AccountCard(
                                    account = account,
                                    onCardClick = { onNavigateToTransactions() }
                                )
                            }
                        }
                    }
                }
                
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.recent_transactions),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        TextButton(onClick = onNavigateToTransactions) {
                            Text(stringResource(R.string.view_all))
                        }
                    }
                }
                
                if (transactionsState.isEmpty()) {
                    item {
                        EmptyState(
                            text = "No recent transactions",
                            icon = Icons.Default.Receipt
                        )
                    }
                } else {
                    items(transactionsState) { transaction ->
                        TransactionItem(transaction = transaction)
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActions(onNavigateToTransfer: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ActionCard(
                title = "Transfer",
                icon = Icons.Default.Send,
                onClick = onNavigateToTransfer,
                color = MaterialTheme.colorScheme.primary
            )
            
            ActionCard(
                title = "Exchange",
                icon = Icons.Default.SwapHoriz,
                onClick = { /* TODO */ },
                color = MaterialTheme.colorScheme.secondary
            )
            
            ActionCard(
                title = "Support",
                icon = Icons.Default.Headset,
                onClick = { /* TODO */ },
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    color: Color
) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun AccountCard(account: Account, onCardClick: () -> Unit) {
    val formatter = NumberFormat.getCurrencyInstance()
    formatter.currency = Currency.getInstance(account.currency)
    
    Card(
        modifier = Modifier
            .width(280.dp)
            .clickable(onClick = onCardClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${account.currency} Account",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
                Chip(
                    onClick = { },
                    colors = ChipDefaults.chipColors(
                        containerColor = if (account.status == "active")
                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                        else
                            MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
                    )
                ) {
                    Text(
                        text = account.status.capitalize(),
                        color = if (account.status == "active")
                            MaterialTheme.colorScheme.tertiary
                        else
                            MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = formatter.format(account.currentBalance),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Text(
                text = "Available: ${formatter.format(account.availableBalance)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Acc: ${formatAccountNumber(account.accountNumber)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onCardClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Transactions")
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                OutlinedButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Details")
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val transactionDate = try {
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(transaction.createdAt)
    } catch (e: Exception) {
        Date()
    }
    val formattedDate = dateFormat.format(transactionDate)
    
    val formatter = NumberFormat.getCurrencyInstance()
    formatter.currency = Currency.getInstance(transaction.currency)
    
    val isOutgoing = transaction.displayAmount != null && transaction.displayAmount < 0
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Transaction icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        when {
                            isOutgoing -> MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                            transaction.transactionType == "deposit" -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
                            else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when {
                        isOutgoing -> Icons.Default.ArrowUpward
                        transaction.transactionType == "deposit" -> Icons.Default.ArrowDownward
                        else -> Icons.Default.SwapHoriz
                    },
                    contentDescription = transaction.transactionType,
                    tint = when {
                        isOutgoing -> MaterialTheme.colorScheme.error
                        transaction.transactionType == "deposit" -> MaterialTheme.colorScheme.tertiary
                        else -> MaterialTheme.colorScheme.primary
                    }
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Transaction details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = when {
                        transaction.transactionType == "transfer" -> "Transfer to ${transaction.receiverEmail ?: "account"}"
                        else -> transaction.transactionType.capitalize()
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            // Amount and status
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = formatter.format(transaction.displayAmount ?: transaction.amount),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        isOutgoing -> MaterialTheme.colorScheme.error
                        transaction.transactionType == "deposit" -> MaterialTheme.colorScheme.tertiary
                        else -> MaterialTheme.colorScheme.primary
                    }
                )
                
                Chip(
                    onClick = { },
                    colors = ChipDefaults.chipColors(
                        containerColor = when(transaction.status) {
                            "completed" -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
                            "pending" -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            else -> MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                        }
                    )
                ) {
                    Text(
                        text = transaction.status.capitalize(),
                        style = MaterialTheme.typography.labelSmall,
                        color = when(transaction.status) {
                            "completed" -> MaterialTheme.colorScheme.tertiary
                            "pending" -> MaterialTheme.colorScheme.secondary
                            else -> MaterialTheme.colorScheme.error
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyState(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.size(48.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )
        }
    }
}

// Helper function to format account number
private fun formatAccountNumber(accountNumber: String): String {
    if (accountNumber.length <= 4) {
        return accountNumber
    }
    val lastFour = accountNumber.takeLast(4)
    val masked = "*".repeat(accountNumber.length - 4)
    return "$masked$lastFour"
}

// Extension to capitalize first letter
private fun String.capitalize(): String {
    return this.replaceFirstChar { 
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() 
    }
} 