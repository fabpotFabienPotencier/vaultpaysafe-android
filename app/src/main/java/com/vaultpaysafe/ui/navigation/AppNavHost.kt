package com.vaultpaysafe.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vaultpaysafe.ui.screens.auth.LoginScreen
import com.vaultpaysafe.ui.screens.auth.RegisterScreen
import com.vaultpaysafe.ui.screens.dashboard.DashboardScreen
import com.vaultpaysafe.ui.screens.transfer.TransferScreen
import com.vaultpaysafe.ui.viewmodels.MainViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val isLoggedIn by mainViewModel.isLoggedIn.collectAsState()
    val startDestination = if (isLoggedIn) {
        Screen.Dashboard.route
    } else {
        Screen.Login.route
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Auth flow
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) },
                onLoginSuccess = { navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }}
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.popBackStack() },
                onRegistrationSuccess = { navController.navigate(Screen.Login.route) }
            )
        }
        
        composable(Screen.ForgotPassword.route) {
            // ForgotPasswordScreen will be implemented later
            // For now we'll redirect to login
            LaunchedEffect(Unit) {
                navController.navigate(Screen.Login.route)
            }
        }
        
        // Main flow
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToTransfer = { navController.navigate(Screen.Transfer.route) },
                onNavigateToTransactions = { navController.navigate(Screen.Transactions.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onLogout = {
                    mainViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Transactions.route) {
            // TransactionsScreen will be implemented later
            // For now we'll redirect to dashboard
            LaunchedEffect(Unit) {
                navController.navigate(Screen.Dashboard.route)
            }
        }
        
        composable(Screen.Transfer.route) {
            TransferScreen(
                onNavigateUp = { navController.popBackStack() },
                onTransferSuccess = { navController.navigate(Screen.Dashboard.route) }
            )
        }
        
        composable(Screen.Profile.route) {
            // ProfileScreen will be implemented later
            // For now we'll redirect to dashboard
            LaunchedEffect(Unit) {
                navController.navigate(Screen.Dashboard.route)
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object Dashboard : Screen("dashboard")
    object Transactions : Screen("transactions")
    object Transfer : Screen("transfer")
    object Profile : Screen("profile")
} 