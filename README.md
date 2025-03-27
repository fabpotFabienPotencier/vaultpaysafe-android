# VaultPaySafe Android App

This is a mobile application for VaultPaySafe - a secure offshore banking platform. The app provides users with the ability to manage their accounts, view transactions, and make transfers securely from their Android devices.

## Features

- **Secure Authentication**: Email and password authentication with secure token storage
- **Dashboard**: View account balances and recent transactions at a glance
- **Accounts**: Manage multiple currency accounts 
- **Transactions**: View transaction history with detailed information
- **Transfers**: Make internal transfers or international transfers
- **Security**: Biometric authentication support and encrypted data storage

## Getting Started

### Prerequisites

- Android Studio 4.2+ or Gradle command line
- Android SDK 24+ (Android 7.0 and above)
- JDK 17

### Building the App

#### Using Gradle Command Line

To build the app without Android Studio, follow these steps:

1. Clone the repository:
```bash
git clone <repository-url>
cd vaultpaysafe-android
```

2. Build the app:
```bash
./gradlew assembleDebug
```

The APK will be generated in `app/build/outputs/apk/debug/app-debug.apk`

#### Using Android Studio

1. Open the project in Android Studio
2. Allow the project to sync and download dependencies
3. Click on the "Run" button or press Shift+F10 to build and run the app

### Running the App

1. Install the APK on your Android device
2. Launch the app
3. Log in with your VaultPaySafe credentials

## Technical Details

- **Architecture**: MVVM architecture pattern with repository pattern
- **UI**: Built using Jetpack Compose for modern, reactive UI
- **Network**: Retrofit for API communication
- **Authentication**: Token-based authentication with secure storage
- **Dependency Injection**: Hilt for dependency injection
- **Concurrency**: Kotlin Coroutines for asynchronous operations
- **Security**: EncryptedSharedPreferences for secure storage

## API Integration

The app integrates with the VaultPaySafe web APIs to provide functionality. API endpoints are defined in `VaultPaySafeApi.kt` interface.

## Security Measures

- Secure token storage using EncryptedSharedPreferences
- Biometric authentication option 
- Secure HTTP communication with certificate pinning
- No sensitive data stored in regular storage
- Automatic session expiration

## Build Types

- **Debug**: For development purposes (not for production)
- **Release**: For production with ProGuard enabled for code obfuscation

## License

This project is proprietary and confidential. Unauthorized copying, redistribution, or usage is strictly prohibited. 