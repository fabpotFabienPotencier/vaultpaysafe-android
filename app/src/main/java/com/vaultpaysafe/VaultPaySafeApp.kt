package com.vaultpaysafe

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VaultPaySafeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any app-wide libraries or configs here
    }
} 