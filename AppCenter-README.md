# VaultPaySafe AppCenter Guide

This guide explains how to set up and build the VaultPaySafe Android app using Microsoft Visual Studio App Center.

## Step 1: Sign up for App Center

1. Go to [App Center](https://appcenter.ms/) and sign up/sign in
2. Click "Add new app" in the dashboard
3. Select "Android" as the platform
4. Name your app "VaultPaySafe" and select your organization
5. Click "Add new app"

## Step 2: Connect your repository

1. Connect App Center to your GitHub, Bitbucket, or Azure DevOps repository
2. Select the repo where you've pushed this project
3. Choose the branch you want to build from (typically 'main' or 'master')

## Step 3: Configure the Build

When configuring your build, use these settings:

1. **Project**: `/app` (This is the default location for Android projects)
2. **Build Variant**: `debug` (for testing) or `release` (for production)
3. **Build Frequency**: Choose "Build this branch on every push" for CI/CD
4. **Signing**: For release builds, add your keystore file in the build settings
5. **Build scripts**: No additional build scripts needed
6. **Distribute Builds**: Choose "Distribute builds" and select your test group

## Step 4: Start your first build

1. Save the configuration
2. Click "Build now"
3. Wait for the build to complete

## Step 5: Download and Install

1. When the build is complete, App Center will send an email to your test group
2. Test users can download and install the app directly from the email link
3. Alternatively, you can download the APK directly from App Center

## Troubleshooting

- **Build Failures**: Check the build logs for detailed error messages
- **Install Issues**: Ensure devices have "Unknown Sources" enabled in settings
- **Missing Icons**: Restart the app after installation if icons don't appear immediately

## Next Steps

- Enable crash reporting by integrating the App Center SDK
- Set up analytics to track app usage
- Configure release branches for different environments (dev, staging, production)

For more information, visit the [App Center Documentation](https://docs.microsoft.com/en-us/appcenter/). 