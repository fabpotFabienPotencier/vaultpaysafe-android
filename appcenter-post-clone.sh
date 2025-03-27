#!/usr/bin/env bash

# This is a post-clone script for App Center builds
# It will be executed after the repository is cloned
# https://docs.microsoft.com/en-us/appcenter/build/custom/scripts/

set -e # Exit immediately if a command exits with a non-zero status
set -x # Print commands and their arguments as they are executed

echo "========== VAULTPAYSAFE ANDROID APP CENTER POST-CLONE SETUP =========="

# Detect Java version
JAVA_VERSION=$(java -version 2>&1 | grep -i version | awk '{print $3}' | tr -d \" | awk -F. '{print $1}')
echo "Java version: $JAVA_VERSION"

# Set up Gradle user home
GRADLE_USER_HOME="${HOME}/.gradle"
mkdir -p "$GRADLE_USER_HOME"

# Create a gradle.properties file compatible with Java 17
cat > "$GRADLE_USER_HOME/gradle.properties" << EOF
org.gradle.jvmargs=-Xmx2048m -XX:+HeapDumpOnOutOfMemoryError
org.gradle.daemon=true
org.gradle.parallel=true
EOF

echo "Created gradle.properties at $GRADLE_USER_HOME/gradle.properties"

# Make gradlew executable
chmod +x "$APPCENTER_SOURCE_DIRECTORY/gradlew"
ls -la "$APPCENTER_SOURCE_DIRECTORY/gradlew"

# Verify gradle wrapper exists
if [ ! -f "$APPCENTER_SOURCE_DIRECTORY/gradle/wrapper/gradle-wrapper.jar" ]; then
    echo "Error: Gradle wrapper JAR not found!"
    ls -la "$APPCENTER_SOURCE_DIRECTORY/gradle/wrapper/"
    exit 1
fi

if [ ! -f "$APPCENTER_SOURCE_DIRECTORY/gradle/wrapper/gradle-wrapper.properties" ]; then
    echo "Error: Gradle wrapper properties not found!"
    ls -la "$APPCENTER_SOURCE_DIRECTORY/gradle/wrapper/"
    exit 1
fi

# Print environment info
echo "APPCENTER_SOURCE_DIRECTORY: $APPCENTER_SOURCE_DIRECTORY"
echo "APPCENTER_BUILD_ID: $APPCENTER_BUILD_ID"
echo "APPCENTER_BRANCH: $APPCENTER_BRANCH"
echo "GRADLE_USER_HOME: $GRADLE_USER_HOME"
echo "Working directory: $(pwd)"

# Ensure Java 17 compatibility
echo "==== CHECKING FOR APP-LEVEL JAVA VERSION COMPATIBILITY ===="
sed -i'.bak' 's/MaxPermSize=512m//' "$APPCENTER_SOURCE_DIRECTORY/app/build.gradle" 2>/dev/null || true
sed -i'.bak' 's/MaxPermSize=512m//' "$APPCENTER_SOURCE_DIRECTORY/gradlew" 2>/dev/null || true

# Print directory structure for debugging
echo "==== PROJECT STRUCTURE ===="
find "$APPCENTER_SOURCE_DIRECTORY" -type f -name "*.gradle" | sort

echo "==== GRADLE WRAPPER PROPERTIES ===="
cat "$APPCENTER_SOURCE_DIRECTORY/gradle/wrapper/gradle-wrapper.properties"

echo "==== APP BUILD.GRADLE ===="
head -50 "$APPCENTER_SOURCE_DIRECTORY/app/build.gradle"

echo "========== POST-CLONE SCRIPT COMPLETED SUCCESSFULLY ==========" 